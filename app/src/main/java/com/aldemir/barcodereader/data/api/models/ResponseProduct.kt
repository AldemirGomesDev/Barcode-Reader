package com.aldemir.barcodereader.data.api.models

import com.aldemir.barcodereader.domain.model.Product

data class ResponseProduct(
    var code: String = "",
    var description: String = "",
    var message: String = "",
    var statusCode: Int = 0
)

fun ResponseProduct.toProduct() = Product(
    code = this.code,
    description = this.description,
    message = this.message,
    statusCode = this.statusCode
)
