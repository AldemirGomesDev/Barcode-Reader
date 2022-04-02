package com.aldemir.barcodereader.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldemir.barcodereader.api.DataStoreManager
import com.aldemir.barcodereader.ui.model.UserLogged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var dataStoreManager: DataStoreManager
) : ViewModel()  {
    fun saveUserSession(userLogged: UserLogged) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.saveToDataStore(userLogged = userLogged)
        }
    }
}