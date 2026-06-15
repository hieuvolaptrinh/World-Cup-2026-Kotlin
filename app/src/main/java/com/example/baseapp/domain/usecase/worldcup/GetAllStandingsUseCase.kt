package com.example.baseapp.domain.usecase.worldcup

import com.example.baseapp.data.local.entity.Standing
import com.example.baseapp.domain.repository.WorldCupRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllStandingsUseCase @Inject constructor(private val repository: WorldCupRepository) {
    operator fun invoke(): Flow<List<Standing>> {
        return repository.getAllStandings()
    }
}
