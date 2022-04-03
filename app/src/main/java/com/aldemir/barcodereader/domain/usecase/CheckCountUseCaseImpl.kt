package com.aldemir.barcodereader.domain.usecase

import com.aldemir.barcodereader.data.repository.CheckCountRepository
import com.aldemir.barcodereader.domain.model.CheckCount
import javax.inject.Inject

class CheckCountUseCaseImpl @Inject constructor(
    private val repository: CheckCountRepository
) : CheckCountUseCase {
    override suspend fun invoke(): CheckCount {
        return repository.checkCount()
    }
}

interface CheckCountUseCase {
    suspend operator fun invoke(): CheckCount

}