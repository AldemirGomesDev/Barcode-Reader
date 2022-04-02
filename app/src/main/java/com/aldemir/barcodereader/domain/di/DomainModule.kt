package com.aldemir.barcodereader.domain.di

import com.aldemir.barcodereader.domain.usecase.LoginUseCase
import com.aldemir.barcodereader.domain.usecase.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindLoginUseCase(useCase: LoginUseCaseImpl) : LoginUseCase
}