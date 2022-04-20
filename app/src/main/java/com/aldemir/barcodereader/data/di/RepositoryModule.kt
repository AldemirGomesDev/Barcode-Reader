package com.aldemir.barcodereader.data.di

import com.aldemir.barcodereader.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindBarCodeReaderRepository(useCase: BarCodeReaderRepositoryImpl) : BarCodeReaderRepository

    @Binds
    fun bindCheckCountRepository(useCase: CheckCountRepositoryImpl) : CheckCountRepository

    @Binds
    fun bindProductRepository(useCase: ProductRepositoryImpl) : ProductRepository
}