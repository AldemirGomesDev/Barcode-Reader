package com.aldemir.barcodereader.data.repository

import com.aldemir.barcodereader.data.api.ApiHelper
import com.aldemir.barcodereader.data.api.models.RequestProduct
import com.aldemir.barcodereader.data.api.models.toProduct
import com.aldemir.barcodereader.data.api.util.Output
import com.aldemir.barcodereader.data.api.util.parseResponse
import com.aldemir.barcodereader.domain.model.Product
import com.aldemir.barcodereader.util.LogUtils
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
) : ProductRepository {
    override suspend fun getProduct(requestProduct: RequestProduct): Product {
        return when (val result = apiHelper.getProducts(requestProduct = requestProduct).parseResponse()) {
            is Output.Success -> {
                LogUtils.debug(message = "response checkCount -> ${result.value}")
                result.value.toProduct()
            }
            is Output.Failure -> Product(statusCode = result.statusCode)
        }
    }
}

interface ProductRepository {
    suspend fun getProduct(requestProduct: RequestProduct): Product
}