package com.aldemir.barcodereader.data.di

import com.aldemir.barcodereader.data.LoginDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun provideLoginDataSource() = LoginDataSource()
}