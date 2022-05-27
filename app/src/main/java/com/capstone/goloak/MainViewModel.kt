package com.capstone.goloak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.goloak.datastore.SettingPreferences
import kotlinx.coroutines.launch

class MainViewModel (private val pref : SettingPreferences) : ViewModel() {

    fun saveSessi(sesi: Boolean){
        viewModelScope.launch {
            pref.saveSesiSetting(sesi)
        }
    }

    fun saveToken(token: String){
        viewModelScope.launch {
            pref.saveTokenSetting(token)
        }
    }

    companion object{
        const val TAG = "MainViewModel"
    }
}