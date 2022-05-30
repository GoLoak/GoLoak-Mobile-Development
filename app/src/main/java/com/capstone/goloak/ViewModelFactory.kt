package com.capstone.goloak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.onboarding.OnBoardingViewModel
import com.capstone.goloak.ui.login.LoginViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(pref) as  T
        } else if (modelClass.isAssignableFrom(OnBoardingViewModel::class.java)){
            return OnBoardingViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("unknow viewmodel class" + modelClass.name)
    }
}