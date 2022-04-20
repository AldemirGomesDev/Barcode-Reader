package com.aldemir.barcodereader.domain.model

import com.aldemir.barcodereader.ui.model.RegisterUiModel

data class Register(
    var message: String = "",
    var statusCode: Int = 0
)

fun Register.toRegisterUiModel() = RegisterUiModel(
    message = this.message,
    statusCode = this.statusCode
)
