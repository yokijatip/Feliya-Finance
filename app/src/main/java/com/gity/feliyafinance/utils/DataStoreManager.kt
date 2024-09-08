package com.gity.feliyafinance.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException


class DataStoreManager(context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("user_preferences")
        val EMAIL_KEY = stringPreferencesKey("email")
    }

    private val dataStore = context.dataStore

    //    Function untuk mendapatkan email
    val emialFlow: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[EMAIL_KEY] ?: ""
        }

    // Function to save email into DataStore
    suspend fun saveEmail(email: String) {
        dataStore.edit {
            it[EMAIL_KEY] = email
        }
    }

    //    Function to Delete email and login
    suspend fun clearEmail() {
        dataStore.edit {
            it.remove(EMAIL_KEY)
        }
    }
}