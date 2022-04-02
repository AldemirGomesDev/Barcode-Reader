package com.aldemir.barcodereader.api

import android.content.Context
import android.content.SharedPreferences
import com.aldemir.barcodereader.MyApplication
import com.aldemir.barcodereader.R
import javax.inject.Inject

class SessionManager @Inject constructor() {
    private var prefs: SharedPreferences = MyApplication.appContext
        .getSharedPreferences(MyApplication.appContext.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

}