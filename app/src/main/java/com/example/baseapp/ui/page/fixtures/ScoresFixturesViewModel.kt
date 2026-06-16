package com.example.baseapp.ui.page.fixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp.data.remote.dto.MatchDto
import com.example.baseapp.data.remote.dto.TeamInfoDto
import com.example.baseapp.domain.repository.WorldCupRepository
import com.example.baseapp.ui.model.MatchUIModel
import com.example.baseapp.ui.model.MatchSectionUIModel
import com.example.baseapp.utils.formatMatchDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ScoresFixturesViewModel @Inject constructor(private val repository: WorldCupRepository) :
        ViewModel() {

    private val _filters = MutableStateFlow<List<String>>(emptyList())
    val filters: StateFlow<List<String>> = _filters.asStateFlow()

    private val _selectedFilter = MutableStateFlow<String?>(null)
    val selectedFilter: StateFlow<String?> = _selectedFilter.asStateFlow()

    private val _sections = MutableStateFlow<List<MatchSectionUIModel>>(emptyList())
    val sections: StateFlow<List<MatchSectionUIModel>> = _sections.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var allSections: List<MatchSectionUIModel> = emptyList()

    init {
        loadMatches()
    }

    fun selectFilter(filter: String) {
        _selectedFilter.value = filter
        _sections.value = allSections.filter { it.title == filter }
    }

    private fun loadMatches() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val matchesDto = repository.getAllMatches()
                val teams = repository.getAllTeams()

                allSections =
                        matchesDto
                                .groupBy { it.sectionTitle() }
                                .toSortedMap(compareBy { sectionOrder(it) })
                                .map { (title, matches) ->
                                    MatchSectionUIModel(
                                            title = title,
                                            matches =
                                                    matches.map { matchDto ->
                                                        matchDto.toMatchUIModel(teams)
                                                    }
                                    )
                                }

                val sectionTitles = allSections.map { it.title }
                _filters.value = sectionTitles

                if (sectionTitles.isNotEmpty()) {
                    selectFilter(sectionTitles.first())
                } else {
                    _sections.value = emptyList()
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load matches"
            } finally {
                _isLoading.value = false
            }
        }
    }



    private fun List<TeamInfoDto>.findTeam(value: String): TeamInfoDto? {
        return firstOrNull {
            it.fifaCode.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true) ||
                    it.nameNormalised.equals(value, ignoreCase = true)
        }
    }
    private fun MatchDto.toMatchUIModel(teams: List<TeamInfoDto>): MatchUIModel {
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

    private fun sectionOrder(title: String): Int {
        return when (title) {
            "Group Stage - Round 1" -> 1
            "Group Stage - Round 2" -> 2
            "Group Stage - Round 3" -> 3
            "Round of 32" -> 4
            "Round of 16" -> 5
            "Quarter-final" -> 6
            "Semi-final" -> 7
            "Match for third place" -> 8
            "Final" -> 9
            else -> 100
        }
    }



}
