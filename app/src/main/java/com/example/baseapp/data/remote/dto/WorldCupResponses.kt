package com.worldcup.app.data.remote.dto

import com.google.gson.annotations.SerializedName

// ==================== MATCHES ====================
data class WorldCupMatchesResponse(
        @SerializedName("name") val name: String,
        @SerializedName("matches") val matches: List<MatchDto>
)

data class MatchDto(
        @SerializedName("round") val round: String,
        @SerializedName("date") val date: String,
        @SerializedName("time") val time: String,
        @SerializedName("team1") val team1: String,
        @SerializedName("team2") val team2: String,
        @SerializedName("score") val score: ScoreDto? = null,
        @SerializedName("goals1") val goals1: List<GoalDto>? = null,
        @SerializedName("goals2") val goals2: List<GoalDto>? = null,
        @SerializedName("group") val group: String? = null,
        @SerializedName("ground") val ground: String,
        @SerializedName("num") val num: Int? = null
)

data class ScoreDto(
        @SerializedName("ft") val ft: List<Int>,
        @SerializedName("ht") val ht: List<Int>
)

data class GoalDto(
        @SerializedName("name") val name: String,
        @SerializedName("minute") val minute: String,
        @SerializedName("penalty") val penalty: Boolean? = null,
        @SerializedName("owngoal") val ownGoal: Boolean? = null
)

// ==================== GROUPS ====================
data class WorldCupGroupsResponse(
        @SerializedName("name") val name: String,
        @SerializedName("groups") val groups: List<GroupDto>
)

data class GroupDto(
        @SerializedName("name") val name: String,
        @SerializedName("teams") val teams: List<String>
)

// ==================== SQUADS ====================
data class SquadResponse(
        @SerializedName("name") val name: String,
        @SerializedName("fifa_code") val fifaCode: String,
        @SerializedName("group") val group: String,
        @SerializedName("players") val players: List<PlayerDto>
)

data class PlayerDto(
        @SerializedName("number") val number: Int,
        @SerializedName("pos") val position: String,
        @SerializedName("name") val name: String,
        @SerializedName("date_of_birth") val dateOfBirth: String
)

// ==================== STADIUMS ====================
data class StadiumsResponse(
        @SerializedName("name") val name: String,
        @SerializedName("stadiums") val stadiums: List<StadiumDto>
)

data class StadiumDto(
        @SerializedName("city") val city: String,
        @SerializedName("timezone") val timezone: String,
        @SerializedName("cc") val countryCode: String,
        @SerializedName("name") val name: String,
        @SerializedName("capacity") val capacity: Int,
        @SerializedName("coords") val coords: String
)

// ==================== TEAMS ====================
data class TeamInfoDto(
        @SerializedName("name") val name: String,
        @SerializedName("name_normalised") val nameNormalised: String? = null,
        @SerializedName("continent") val continent: String,
        @SerializedName("flag_icon") val flagIcon: String,
        @SerializedName("flag_unicode") val flagUnicode: String,
        @SerializedName("fifa_code") val fifaCode: String,
        @SerializedName("group") val group: String,
        @SerializedName("confed") val confed: String
)
