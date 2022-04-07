package com.aldemir.barcodereader.data.api.models

import com.aldemir.barcodereader.domain.model.Register

data class ResponseRegister(
    var message: String = "",
    var statusCode: Int = 0
)

fun ResponseRegister.toRegister() = Register(
    message = this.message,
    statusCode = this.statusCode
)
