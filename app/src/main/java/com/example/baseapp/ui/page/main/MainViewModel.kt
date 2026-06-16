package com.worldcup.app.ui.page.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldcup.app.data.remote.dto.MatchDto
import com.worldcup.app.data.remote.dto.TeamInfoDto
import com.worldcup.app.domain.repository.WorldCupRepository
import com.worldcup.app.ui.model.MatchTodayUIModel
import com.worldcup.app.utils.formatMatchDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WorldCupRepository
) : ViewModel() {

    private val _matches = MutableStateFlow<List<MatchTodayUIModel>>(emptyList())
    val matches: StateFlow<List<MatchTodayUIModel>> = _matches.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadTodayMatches()
    }

    private fun loadTodayMatches() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val matchesDto = repository.getAllMatches()
                val teams = repository.getAllTeams()

                val today = LocalDate.now()

                val todayMatches = matchesDto
                    .filter { matchDto ->
                        formatMatchDateTime(matchDto.date, matchDto.time).date == today
                    }
                    .map { matchDto ->
                        matchDto.toMatchTodayModel(teams)
                    }

                _matches.value = todayMatches

            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load today matches"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun MatchDto.toMatchTodayModel(teams: List<TeamInfoDto>): MatchTodayUIModel {
        val homeTeam = teams.findTeam(team1)
        val awayTeam = teams.findTeam(team2)
        val localDateTime = formatMatchDateTime(date, time)

        return MatchTodayUIModel(
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
            round = sectionTitle()
        )
    }

    private fun List<TeamInfoDto>.findTeam(value: String): TeamInfoDto? {
        return firstOrNull {
            it.fifaCode.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true) ||
                    it.nameNormalised.equals(value, ignoreCase = true)
        }
    }

    private fun MatchDto.sectionTitle(): String {
        if (group != null && round.startsWith("Matchday", ignoreCase = true)) {
            return "Group Stage - Round ${groupStageRound()}"
        }
        return round
    }

    private fun MatchDto.groupStageRound(): Int {
        val matchday = round.substringAfter("Matchday", "").trim().toIntOrNull() ?: return 1
        return when (matchday) {
            in 1..7 -> 1
            in 8..13 -> 2
            else -> 3
        }
    }

}
