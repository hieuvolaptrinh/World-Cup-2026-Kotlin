package com.worldcup.app.domain.usecase.login

import com.worldcup.app.data.local.entity.LoginPassword
import com.worldcup.app.domain.repository.LoginRepository
import javax.inject.Inject

class GetAllLoginsUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(): List<LoginPassword> {
        return repository.getAll()
    }
}