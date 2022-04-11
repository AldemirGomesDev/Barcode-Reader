package com.aldemir.barcodereader.domain.usecase

import com.aldemir.barcodereader.data.api.models.RequestProduct
import com.aldemir.barcodereader.data.repository.ProductRepository
import com.aldemir.barcodereader.domain.model.Product
import javax.inject.Inject

class ProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : ProductUseCase {
    override suspend fun invoke(requestProduct: RequestProduct): Product {
        return productRepository.getProduct(requestProduct = requestProduct)
    }
}

interface ProductUseCase {
    suspend operator fun invoke(requestProduct: RequestProduct): Product
}