package com.worldcup.app.domain.usecase.worldcup

import com.worldcup.app.data.remote.dto.GroupDto
import com.worldcup.app.domain.repository.WorldCupRepository
import javax.inject.Inject

class GetAllGroupsUseCase @Inject constructor(
    private val repository: WorldCupRepository
) {
    suspend operator fun invoke(): List<GroupDto> {
        return repository.getAllGroups()
    }
}
