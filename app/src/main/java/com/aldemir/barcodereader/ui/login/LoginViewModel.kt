package com.aldemir.barcodereader.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.aldemir.barcodereader.R
import com.aldemir.barcodereader.data.api.DataStoreManager
import com.aldemir.barcodereader.data.api.models.RequestLogin
import com.aldemir.barcodereader.ui.model.UserLogged
import com.aldemir.barcodereader.domain.usecase.LoginUseCase
import com.aldemir.barcodereader.util.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _userSession = MutableLiveData<UserLogged>()
    val userSession: LiveData<UserLogged> = _userSession

    fun login(username: String, password: String) {
        val requestLogin = RequestLogin(phone = username, password = password)
        viewModelScope.launch(Dispatchers.IO) {
            val userLogged = loginUseCase.invoke(requestLogin = requestLogin)
            if (userLogged != null) {
                _userSession.run { postValue(userLogged) }
                saveUserSession(userLogged = userLogged)
                LogUtils.info(tag = "userSession", message = userLogged.token)
            }
        }
    }

    private fun saveUserSession(userLogged: UserLogged) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.saveToDataStore(userLogged = userLogged)
        }
    }

//    fun getUserSession() {
//        viewModelScope.launch(Dispatchers.IO) {
//            dataStoreManager.getFromDataStore().catch { e ->
//                LogUtils.error(MainActivity.TAG, e.toString())
//            }.collect { userSession ->
//                _userSession.postValue(userSession)
//                LogUtils.info(MainActivity.TAG, "userLogged: $userSession")
//            }
//
//        }
//    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 3
    }
}