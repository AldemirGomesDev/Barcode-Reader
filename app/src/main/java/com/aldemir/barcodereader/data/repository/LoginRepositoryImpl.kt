package com.aldemir.barcodereader.data.repository

import com.aldemir.barcodereader.api.ApiHelper
import com.aldemir.barcodereader.api.models.RequestLogin
import com.aldemir.barcodereader.api.models.toUserLogged
import com.aldemir.barcodereader.api.util.Output
import com.aldemir.barcodereader.api.util.parseResponse
import com.aldemir.barcodereader.ui.model.UserLogged
import com.aldemir.barcodereader.util.LogUtils
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    ) : LoginRepository {

    override suspend fun login(requestLogin: RequestLogin): UserLogged? {
        return when (val result = apiHelper.login(requestLogin).parseResponse()) {
            is Output.Success -> {
                LogUtils.debug(message = "response -> ${result.value}")
                result.value.toUserLogged()
            }
            is Output.Failure -> UserLogged(statusCode = result.statusCode)
        }
    }
}

interface LoginRepository {
    suspend fun login(requestLogin: RequestLogin) : UserLogged?
}