package com.aldemir.barcodereader.data.api.models

data class ResponseProduct(
    var code: String = "",
    var description: String = "",
    var message: String = "",
    var statusCode: Int = 0
)
