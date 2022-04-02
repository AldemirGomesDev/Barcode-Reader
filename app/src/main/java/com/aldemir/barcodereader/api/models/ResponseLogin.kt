package com.aldemir.barcodereader.api.models

import com.aldemir.barcodereader.ui.model.UserLogged

data class ResponseLogin(
    var token: String = "",
    var message: String = "",
    var statusCode: Int = 0
)

fun ResponseLogin.toUserLogged() = UserLogged(
    token = this.token,
    message = this.message,
    statusCode = this.statusCode
)
