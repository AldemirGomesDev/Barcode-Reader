package com.aldemir.barcodereader.data.di

import com.aldemir.barcodereader.data.repository.CheckCountRepository
import com.aldemir.barcodereader.data.repository.CheckCountRepositoryImpl
import com.aldemir.barcodereader.data.repository.LoginRepository
import com.aldemir.barcodereader.data.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindLoginRepository(useCase: LoginRepositoryImpl) : LoginRepository

    @Binds
    fun bindCheckCountRepository(useCase: CheckCountRepositoryImpl) : CheckCountRepository
}