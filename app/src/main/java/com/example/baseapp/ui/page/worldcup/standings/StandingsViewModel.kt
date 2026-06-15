package com.example.baseapp.ui.page.worldcup.standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp.domain.usecase.worldcup.container.WorldCupUseCases
import com.example.baseapp.ui.page.worldcup.model.StandingWithTeam
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class StandingsViewModel @Inject constructor(private val useCases: WorldCupUseCases) : ViewModel() {

    private val _groups = MutableStateFlow<List<String>>(emptyList())
    val groups: StateFlow<List<String>> = _groups.asStateFlow()

    private val _selectedGroup = MutableStateFlow<String?>(null)
    val selectedGroup: StateFlow<String?> = _selectedGroup.asStateFlow()

    private val _standings = MutableStateFlow<List<StandingWithTeam>>(emptyList())
    val standings: StateFlow<List<StandingWithTeam>> = _standings.asStateFlow()

    init {
        loadGroups()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            try {
                val groups = useCases.getAllGroups()
                _groups.value = groups
                if (groups.isNotEmpty()) {
                    selectGroup(groups.first())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectGroup(group: String) {
        _selectedGroup.value = group
        viewModelScope.launch {
            useCases.getStandingsByGroup(group)
                    .map { standings ->
                        standings
                                .map { standing ->
                                    val team = useCases.getTeamById(standing.teamId)
                                    if (team != null) {
                                        StandingWithTeam(standing, team)
                                    } else null
                                }
                                .filterNotNull()
                    }
                    .collect { _standings.value = it }
        }
    }
}
