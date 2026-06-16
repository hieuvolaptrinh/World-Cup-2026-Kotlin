package com.example.baseapp.domain.usecase.worldcup

import com.example.baseapp.data.remote.dto.MatchDto
import com.example.baseapp.domain.repository.WorldCupRepository
import javax.inject.Inject

class GetAllMatchesUseCase @Inject constructor(
    private val repository: WorldCupRepository
) {
    suspend operator fun invoke(): List<MatchDto> {
        return repository.getAllMatches()
    }
}
