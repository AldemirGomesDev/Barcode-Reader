package com.aldemir.barcodereader.domain.di

import com.aldemir.barcodereader.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindLoginUseCase(useCase: LoginUseCaseImpl) : LoginUseCase

    @Binds
    fun bindCheckCountUseCase(useCase: CheckCountUseCaseImpl) : CheckCountUseCase

    @Binds
    fun bindRegisterUseCase(useCase: RegisterUseCaseImpl) : RegisterUseCase
}