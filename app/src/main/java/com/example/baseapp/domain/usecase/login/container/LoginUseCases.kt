package com.worldcup.app.domain.usecase.login.container

import com.worldcup.app.domain.usecase.login.GetAllLoginsUseCase
import com.worldcup.app.domain.usecase.login.GetLoginByNameUseCase

data class LoginUseCases(
    val getAll: GetAllLoginsUseCase,
    val getByName: GetLoginByNameUseCase
)