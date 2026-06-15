package com.example.baseapp.domain.usecase.worldcup

import com.example.baseapp.domain.repository.WorldCupRepository
import javax.inject.Inject

class GetAllGroupsUseCase @Inject constructor(private val repository: WorldCupRepository) {
    suspend operator fun invoke(): List<String> {
        return repository.getAllGroups()
    }
}
