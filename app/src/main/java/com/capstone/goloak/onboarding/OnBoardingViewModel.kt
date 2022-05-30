package com.capstone.goloak.onboarding

import androidx.lifecycle.*
import com.capstone.goloak.datastore.SettingPreferences
import kotlinx.coroutines.launch

class OnBoardingViewModel(private val pref : SettingPreferences) : ViewModel() {
    fun saveSesi(sesi: Boolean){
        viewModelScope.launch {
            pref.saveSesiObSetting(sesi)
        }
    }

    fun getSesi(): LiveData<Boolean> {
        return pref.getSesiObSetting().asLiveData()
    }
}