package com.example.baseapp.domain.usecase.worldcup

import com.example.baseapp.data.remote.dto.TeamInfoDto
import com.example.baseapp.domain.repository.WorldCupRepository
import javax.inject.Inject

class GetAllTeamsUseCase @Inject constructor(
    private val repository: WorldCupRepository
) {
    suspend operator fun invoke(): List<TeamInfoDto> {
        return repository.getAllTeams()
    }
}
