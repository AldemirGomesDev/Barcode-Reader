package com.aldemir.barcodereader.data.repository

import com.aldemir.barcodereader.data.api.ApiHelper
import com.aldemir.barcodereader.data.api.models.RequestLogin
import com.aldemir.barcodereader.data.api.models.RequestRegister
import com.aldemir.barcodereader.data.api.models.toRegister
import com.aldemir.barcodereader.data.api.models.toUserLogged
import com.aldemir.barcodereader.data.api.util.Output
import com.aldemir.barcodereader.data.api.util.parseResponse
import com.aldemir.barcodereader.domain.model.Register
import com.aldemir.barcodereader.ui.model.UserLogged
import com.aldemir.barcodereader.util.LogUtils
import javax.inject.Inject

class BarCodeReaderRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    ) : BarCodeReaderRepository {

    override suspend fun login(requestLogin: RequestLogin): UserLogged? {
        return when (val result = apiHelper.login(requestLogin).parseResponse()) {
            is Output.Success -> {
                LogUtils.debug(message = "response -> ${result.value}")
                result.value.toUserLogged()
            }
            is Output.Failure -> UserLogged(statusCode = result.statusCode)
        }
    }

    override suspend fun register(requestRegister: RequestRegister): Register {
        return when (val result = apiHelper.register(requestRegister).parseResponse()) {
            is Output.Success -> {
                LogUtils.debug(message = "response -> ${result.value}")
                result.value.toRegister()
            }
            is Output.Failure -> Register(statusCode = result.statusCode)
        }
    }
}

interface BarCodeReaderRepository {
    suspend fun login(requestLogin: RequestLogin) : UserLogged?
    suspend fun register(requestRegister: RequestRegister) : Register
}