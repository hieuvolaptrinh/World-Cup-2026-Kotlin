package com.example.baseapp.ui.page.fixtures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp.data.remote.dto.GroupDto
import com.example.baseapp.domain.repository.WorldCupRepository
import com.example.baseapp.ui.model.MatchUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ScoresFixturesViewModel @Inject constructor(private val repository: WorldCupRepository) :
        ViewModel() {

    private val _groups = MutableStateFlow<List<GroupDto>>(emptyList())
    val groups: StateFlow<List<GroupDto>> = _groups.asStateFlow()

    private val _selectedGroup = MutableStateFlow<GroupDto?>(null)
    val selectedGroup: StateFlow<GroupDto?> = _selectedGroup.asStateFlow()

    private val _matches = MutableStateFlow<List<MatchUiModel>>(emptyList())
    val matches: StateFlow<List<MatchUiModel>> = _matches.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadGroups()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val groupsList = repository.getAllGroups()
                _groups.value = groupsList
                if (groupsList.isNotEmpty()) {
                    selectGroup(groupsList[0])
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load groups"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectGroup(group: GroupDto) {
        _selectedGroup.value = group
        loadMatchesForGroup(group.name)
    }

    private fun loadMatchesForGroup(groupName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val matchesDto = repository.getMatchesByGroup(groupName)
                val teams = repository.getAllTeams()

                val matchUiModels =
                        matchesDto.map { matchDto ->
                            val homeTeam = teams.find { it.fifaCode == matchDto.team1 }
                            val awayTeam = teams.find { it.fifaCode == matchDto.team2 }

                            MatchUiModel(
                                    id = matchDto.num?.toString() ?: matchDto.date + matchDto.time,
                                    homeTeamName = homeTeam?.name ?: matchDto.team1,
                                    homeTeamFlag = homeTeam?.flagIcon ?: "",
                                    awayTeamName = awayTeam?.name ?: matchDto.team2,
                                    awayTeamFlag = awayTeam?.flagIcon ?: "",
                                    homeScore = matchDto.score?.ft?.getOrNull(0)?.toString() ?: "-",
                                    awayScore = matchDto.score?.ft?.getOrNull(1)?.toString() ?: "-",
                                    matchDate = formatDate(matchDto.date),
                                    matchTime = matchDto.time,
                                    status = if (matchDto.score != null) "FT" else "SCHEDULED",
                                    stadium = matchDto.ground
                            )
                        }

                _matches.value = matchUiModels
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load matches"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun formatDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate ?: Date())
        } catch (e: Exception) {
            date
        }
    }
}
