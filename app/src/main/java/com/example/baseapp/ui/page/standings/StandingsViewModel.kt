package com.example.baseapp.ui.page.standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp.data.remote.dto.GroupDto
import com.example.baseapp.domain.repository.WorldCupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StandingUiModel(
        val position: Int,
        val teamName: String,
        val teamFlag: String,
        val played: Int,
        val won: Int,
        val drawn: Int,
        val lost: Int,
        val goalsFor: Int,
        val goalsAgainst: Int,
        val goalDifference: Int,
        val points: Int
)

@HiltViewModel
class StandingsViewModel @Inject constructor(private val repository: WorldCupRepository) :
        ViewModel() {

    private val _groups = MutableStateFlow<List<GroupDto>>(emptyList())
    val groups: StateFlow<List<GroupDto>> = _groups.asStateFlow()

    private val _selectedGroup = MutableStateFlow<GroupDto?>(null)
    val selectedGroup: StateFlow<GroupDto?> = _selectedGroup.asStateFlow()

    private val _standings = MutableStateFlow<List<StandingUiModel>>(emptyList())
    val standings: StateFlow<List<StandingUiModel>> = _standings.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

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
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectGroup(group: GroupDto) {
        _selectedGroup.value = group
        loadStandingsForGroup(group)
    }

    private fun loadStandingsForGroup(group: GroupDto) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val teams = repository.getAllTeams()

                // Create standings from group teams (placeholder data)
                val standingsUi =
                        group.teams.mapIndexed { index, teamCode ->
                            val team = teams.find { it.fifaCode == teamCode }
                            StandingUiModel(
                                    position = index + 1,
                                    teamName = team?.name ?: teamCode,
                                    teamFlag = team?.flagIcon ?: "",
                                    played = 0,
                                    won = 0,
                                    drawn = 0,
                                    lost = 0,
                                    goalsFor = 0,
                                    goalsAgainst = 0,
                                    goalDifference = 0,
                                    points = 0
                            )
                        }

                _standings.value = standingsUi
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
