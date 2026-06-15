package com.example.baseapp.ui.page.worldcup.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseapp.domain.usecase.worldcup.container.WorldCupUseCases
import com.example.baseapp.ui.page.worldcup.model.MatchWithTeams
import com.example.baseapp.ui.page.worldcup.model.StandingWithTeam
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class WorldCupHomeViewModel @Inject constructor(private val useCases: WorldCupUseCases) :
        ViewModel() {

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val _recentMatches = MutableStateFlow<List<MatchWithTeams>>(emptyList())
    val recentMatches: StateFlow<List<MatchWithTeams>> = _recentMatches.asStateFlow()

    private val _groupStandings = MutableStateFlow<Map<String, List<StandingWithTeam>>>(emptyMap())
    val groupStandings: StateFlow<Map<String, List<StandingWithTeam>>> =
            _groupStandings.asStateFlow()

    init {
        initializeData()
        loadHomeData()
    }

    private fun initializeData() {
        viewModelScope.launch {
            try {
                useCases.initializeSampleData()
                _isInitialized.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            // Load recent matches (first 4 matches)
            useCases.getAllMatches()
                    .map { matches ->
                        matches.take(4)
                                .map { match ->
                                    val homeTeam = useCases.getTeamById(match.homeTeamId)
                                    val awayTeam = useCases.getTeamById(match.awayTeamId)
                                    if (homeTeam != null && awayTeam != null) {
                                        MatchWithTeams(match, homeTeam, awayTeam)
                                    } else null
                                }
                                .filterNotNull()
                    }
                    .collect { _recentMatches.value = it }
        }

        viewModelScope.launch {
            // Load group standings (first group only for preview)
            useCases.getAllStandings()
                    .map { standings ->
                        standings.groupBy { it.groupName }.mapValues { (_, groupStandings) ->
                            groupStandings
                                    .take(4)
                                    .map { standing ->
                                        val team = useCases.getTeamById(standing.teamId)
                                        if (team != null) {
                                            StandingWithTeam(standing, team)
                                        } else null
                                    }
                                    .filterNotNull()
                        }
                    }
                    .collect { _groupStandings.value = it }
        }
    }
}
