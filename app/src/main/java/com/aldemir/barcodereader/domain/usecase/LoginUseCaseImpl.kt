package com.aldemir.barcodereader.domain.usecase

import com.aldemir.barcodereader.data.api.models.RequestLogin
import com.aldemir.barcodereader.data.repository.LoginRepository
import com.aldemir.barcodereader.ui.model.UserLogged
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : LoginUseCase {
    override suspend fun invoke(requestLogin: RequestLogin): UserLogged? {
        return loginRepository.login(requestLogin)
    }
}

interface LoginUseCase {
    suspend operator fun invoke(requestLogin: RequestLogin): UserLogged?

}