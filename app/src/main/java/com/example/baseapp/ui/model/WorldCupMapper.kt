package com.worldcup.app.ui.model

import com.worldcup.app.data.local.entity.WorldCupMatchEntity
import com.worldcup.app.data.local.entity.WorldCupTeamEntity
import com.worldcup.app.data.remote.dto.MatchDto
import com.worldcup.app.data.remote.dto.TeamInfoDto
import com.worldcup.app.utils.formatMatchDateTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Locale

fun TeamInfoDto.toEntity(): WorldCupTeamEntity {
    return WorldCupTeamEntity(
        name = name,
        nameNormalised = nameNormalised,
        continent = continent,
        flagIcon = flagIcon,
        flagUnicode = flagUnicode,
        fifaCode = fifaCode,
        groupName = group,
        confed = confed
    )
}

fun MatchDto.toEntity(teams: List<TeamInfoDto>): WorldCupMatchEntity {
    val kickoffMillis = parseKickoffMillis(date, time)
    val localMatchDate = kickoffMillis?.toLocalDateString()
    val homeTeam = teams.findTeam(team1)
    val awayTeam = teams.findTeam(team2)
    // Prefer full-time score; fall back to half-time score when FT is not available yet.
    val scorePair = score?.ft?.takeIf { it.size >= 2 } ?: score?.ht?.takeIf { it.size >= 2 }

    return WorldCupMatchEntity(
        id = num?.toString() ?: buildMatchId(date, time, team1, team2),
        round = sectionTitle(),
        date = date,
        time = time,
        kickoffMillis = kickoffMillis,
        localMatchDate = localMatchDate,
        homeTeamName = homeTeam?.name ?: team1,
        homeTeamFlag = homeTeam?.flagUnicode ?: homeTeam?.flagIcon ?: "",
        awayTeamName = awayTeam?.name ?: team2,
        awayTeamFlag = awayTeam?.flagUnicode ?: awayTeam?.flagIcon ?: "",
        homeScore = scorePair?.getOrNull(0),
        awayScore = scorePair?.getOrNull(1),
        groupName = group,
        stadium = ground,
        lastUpdatedAt = System.currentTimeMillis()
    )
}

fun buildMatchId(
    date: String,
    time: String,
    team1: String,
    team2: String
): String {
    return "$date-$time-$team1-$team2"
        .lowercase()
        .replace(" ", "_")
        .replace(":", "_")
}

fun parseKickoffMillis(date: String, time: String): Long? {
    return runCatching {
        // API time is like "20:00 UTC-6"; convert that source offset into epoch millis.
        val sourceDate = LocalDate.parse(date)
        val sourceTime = LocalTime.parse(time.substringBefore(" "))
        val sourceOffset = parseUtcOffset(time.substringAfter("UTC", "UTC+0").trim())

        OffsetDateTime.of(sourceDate, sourceTime, sourceOffset)
            .toInstant()
            .toEpochMilli()
    }.getOrNull()
}

fun parseUtcOffset(offsetText: String): ZoneOffset {
    val normalized = offsetText.ifBlank { "+0" }
    val sign = if (normalized.startsWith("-")) "-" else "+"
    val rawHours = normalized.removePrefix("+").removePrefix("-")
    val parts = rawHours.split(":")
    val hours = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val minutes = parts.getOrNull(1)?.toIntOrNull() ?: 0

    return ZoneOffset.of(String.format(Locale.US, "%s%02d:%02d", sign, hours, minutes))
}

fun Long.toLocalDateString(): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .toString()
}

fun MatchDto.toMatchUIModel(teams: List<TeamInfoDto>): MatchUIModel {
    val homeTeam = teams.findTeam(team1)
    val awayTeam = teams.findTeam(team2)
    val localDateTime = formatMatchDateTime(date, time)

    return MatchUIModel(
        id = num?.toString() ?: "$date-$time-$team1-$team2",
        homeTeamName = homeTeam?.name ?: team1,
        homeTeamFlag = homeTeam?.flagUnicode ?: "",
        awayTeamName = awayTeam?.name ?: team2,
        awayTeamFlag = awayTeam?.flagUnicode ?: "",
        homeScore = score?.ft?.getOrNull(0)?.toString() ?: "-",
        awayScore = score?.ft?.getOrNull(1)?.toString() ?: "-",
        matchDate = localDateTime.displayDate,
        matchTime = localDateTime.displayTime,
        status = if (score != null) "FT" else "SCHEDULED",
        stadium = null
    )
}

fun MatchDto.sectionTitle(): String {
    if (group != null && round.startsWith("Matchday", ignoreCase = true)) {
        return "Group Stage - Round ${groupStageRound()}"
    }
    return round
}

fun MatchDto.groupStageRound(): Int {
    val matchday = round.substringAfter("Matchday", "").trim().toIntOrNull() ?: return 1
    return when (matchday) {
        in 1..7 -> 1
        in 8..13 -> 2
        else -> 3
    }
}

fun List<TeamInfoDto>.findTeam(value: String): TeamInfoDto? {
    return firstOrNull {
        it.fifaCode.equals(value, ignoreCase = true) ||
                it.name.equals(value, ignoreCase = true) ||
                it.nameNormalised.equals(value, ignoreCase = true)
    }
}
