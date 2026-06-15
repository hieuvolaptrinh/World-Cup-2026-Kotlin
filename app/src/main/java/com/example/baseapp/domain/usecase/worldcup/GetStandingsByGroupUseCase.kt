package com.example.baseapp.domain.usecase.worldcup

import com.example.baseapp.data.local.entity.Standing
import com.example.baseapp.domain.repository.WorldCupRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetStandingsByGroupUseCase @Inject constructor(private val repository: WorldCupRepository) {
    operator fun invoke(groupName: String): Flow<List<Standing>> {
        return repository.getStandingsByGroup(groupName)
    }
}
