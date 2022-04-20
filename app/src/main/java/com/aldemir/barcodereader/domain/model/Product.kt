package com.aldemir.barcodereader.domain.model

import com.aldemir.barcodereader.ui.model.ProductUiModel

data class Product(
    var code: String = "",
    var description: String = "",
    var message: String = "",
    var statusCode: Int = 0
)

fun Product.toProductUiModel() = ProductUiModel(
    code = this.code,
    description = this.description,
    message = this.message,
    statusCode = this.statusCode
)
