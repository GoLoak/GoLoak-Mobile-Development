package com.capstone.goloak.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val sesiKey = booleanPreferencesKey("sesi_setting")
    private val sesiObKey = booleanPreferencesKey("sesi_ob_setting")
    private val tokenKey = stringPreferencesKey("token_setting")

    suspend fun saveSesiSetting(sesi: Boolean) {
        dataStore.edit { preferences ->
            preferences[sesiKey] = sesi
        }
    }

    suspend fun saveSesiObSetting(sesi: Boolean) {
        dataStore.edit { preferences ->
            preferences[sesiObKey] = sesi
        }
    }

    fun getSesiSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[sesiKey] ?: false
        }
    }

    fun getSesiObSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[sesiObKey] ?: false
        }
    }

    suspend fun saveTokenSetting(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
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