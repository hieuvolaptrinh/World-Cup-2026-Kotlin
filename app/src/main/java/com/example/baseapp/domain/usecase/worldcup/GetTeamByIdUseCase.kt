package com.example.baseapp.domain.usecase.worldcup

import com.example.baseapp.data.local.entity.Team
import com.example.baseapp.domain.repository.WorldCupRepository
import javax.inject.Inject

class GetTeamByIdUseCase @Inject constructor(private val repository: WorldCupRepository) {
    suspend operator fun invoke(teamId: String): Team? {
        return repository.getTeamById(teamId)
    }
}
