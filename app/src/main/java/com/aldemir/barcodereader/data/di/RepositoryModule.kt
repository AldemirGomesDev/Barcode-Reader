package com.aldemir.barcodereader.data.di

import com.aldemir.barcodereader.data.LoginRepository
import com.aldemir.barcodereader.data.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindLoginRepository(useCase: LoginRepositoryImpl) : LoginRepository
}