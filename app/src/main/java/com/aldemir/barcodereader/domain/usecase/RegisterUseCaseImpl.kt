package com.aldemir.barcodereader.domain.usecase

import com.aldemir.barcodereader.data.api.models.RequestRegister
import com.aldemir.barcodereader.data.repository.BarCodeReaderRepository
import com.aldemir.barcodereader.domain.model.Register
import javax.inject.Inject


class RegisterUseCaseImpl @Inject constructor(
    private val barCodeReaderRepository: BarCodeReaderRepository
) : RegisterUseCase {
    override suspend fun invoke(requestRegister: RequestRegister): Register {
        return barCodeReaderRepository.register(requestRegister = requestRegister)
    }
}

interface RegisterUseCase {
    suspend operator fun invoke(requestRegister: RequestRegister): Register

}