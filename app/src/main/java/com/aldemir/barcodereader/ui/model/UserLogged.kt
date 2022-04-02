package com.aldemir.barcodereader.ui.model

data class UserLogged(
    var token: String = "",
    var message: String = "",
    var statusCode: Int = 0
)
