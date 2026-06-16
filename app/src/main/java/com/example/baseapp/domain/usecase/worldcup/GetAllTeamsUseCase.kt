package com.worldcup.app.domain.usecase.worldcup

import com.worldcup.app.data.remote.dto.TeamInfoDto
import com.worldcup.app.domain.repository.WorldCupRepository
import javax.inject.Inject

class GetAllTeamsUseCase @Inject constructor(
    private val repository: WorldCupRepository
) {
    suspend operator fun invoke(): List<TeamInfoDto> {
        return repository.getAllTeams()
    }
}
