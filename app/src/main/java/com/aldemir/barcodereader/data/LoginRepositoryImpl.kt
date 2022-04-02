package com.aldemir.barcodereader.data

import com.aldemir.barcodereader.api.ApiHelper
import com.aldemir.barcodereader.api.ApiService
import com.aldemir.barcodereader.api.models.RequestLogin
import com.aldemir.barcodereader.api.models.ResponseLogin
import com.aldemir.barcodereader.api.models.toUserLogged
import com.aldemir.barcodereader.data.model.LoggedInUser
import com.aldemir.barcodereader.ui.model.UserLogged
import com.aldemir.barcodereader.util.LogUtils
import com.aldemir.barcodereader.util.Status
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    ) : LoginRepository {

    override suspend fun login(requestLogin: RequestLogin): UserLogged? {
        var userLogged: UserLogged? = null
        val response = apiHelper.login(requestLogin)
        if (response.isSuccessful) {
            if (response.body() != null) {
                userLogged = response.body()!!.data!!.toUserLogged()
            }
        } else {
            LogUtils.error(message = response.message())
        }

        return userLogged
    }
}

interface LoginRepository {
    suspend fun login(requestLogin: RequestLogin) : UserLogged?
}