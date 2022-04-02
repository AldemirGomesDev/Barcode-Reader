package com.aldemir.barcodereader.api

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aldemir.barcodereader.MyApplication
import com.aldemir.barcodereader.ui.model.UserLogged
import com.aldemir.barcodereader.util.Constants
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor() {
    companion object {
        private const val DATASTORE_NAME = "user_preferences"
        private val Context.counterDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )

        val TOKEN = stringPreferencesKey(Constants.TOKEN)

    }

    suspend fun saveToDataStore(userLogged: UserLogged) {
        MyApplication.appContext.counterDataStore.edit {preferences ->

            preferences[TOKEN] = userLogged.token

        }
    }
    suspend fun getFromDataStore() = MyApplication.appContext.counterDataStore.data.map { preferences ->
        UserLogged(
            token = preferences[TOKEN]?:"",
        )
    }
}