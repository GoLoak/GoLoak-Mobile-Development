package com.capstone.goloak

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.goloak.datastore.SettingPreferences
import kotlinx.coroutines.launch

class MainViewModel (private val pref : SettingPreferences) : ViewModel() {

    fun getSesi(): LiveData<Boolean> {
        return pref.getSesiSetting().asLiveData()
    }
}