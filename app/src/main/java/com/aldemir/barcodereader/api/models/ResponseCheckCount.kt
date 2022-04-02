package com.aldemir.barcodereader.api.models

data class ResponseCheckCount(
    var status: Boolean = false,
    var message: String = "",
    var statusCode: Int = 0
)
