package com.worldcup.app.domain.usecase.worldcup

import com.worldcup.app.data.remote.dto.MatchDto
import com.worldcup.app.domain.repository.WorldCupRepository
import javax.inject.Inject

class GetMatchesByGroupUseCase @Inject constructor(private val repository: WorldCupRepository) {
    suspend operator fun invoke(group: String): List<MatchDto> {
        return repository.getMatchesByGroup(group)
    }
}
