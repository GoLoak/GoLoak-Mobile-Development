package com.capstone.goloak.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

class SettingPreferences private constructor(private val dataStrore: DataStore<Preferences>) {
    private val SESI_KEY = booleanPreferencesKey("sesi_setting")
    private val TOKEN_KEY = stringPreferencesKey("token_setting")

    suspend fun saveSesiSetting(sesi: Boolean) {
        dataStrore.edit { preferences ->
            preferences[SESI_KEY] = sesi
        }
    }

    suspend fun saveTokenSetting(token: String) {
        dataStrore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}