package com.aldemir.barcodereader.domain.usecase

import com.aldemir.barcodereader.data.api.models.RequestLogin
import com.aldemir.barcodereader.data.repository.BarCodeReaderRepository
import com.aldemir.barcodereader.ui.model.UserLogged
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val barCodeReaderRepository: BarCodeReaderRepository
) : LoginUseCase {
    override suspend fun invoke(requestLogin: RequestLogin): UserLogged? {
        return barCodeReaderRepository.login(requestLogin)
    }
}

interface LoginUseCase {
    suspend operator fun invoke(requestLogin: RequestLogin): UserLogged?

}