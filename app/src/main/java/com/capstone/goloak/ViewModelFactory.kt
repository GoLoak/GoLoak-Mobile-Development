package com.capstone.goloak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.onboarding.OnBoardingViewModel
import com.capstone.goloak.ui.camera.CameraViewModel
import com.capstone.goloak.ui.history.HistoryViewModel
import com.capstone.goloak.ui.home.HomeViewModel
import com.capstone.goloak.ui.login.LoginViewModel
import com.capstone.goloak.ui.point.PointViewModel
import com.capstone.goloak.ui.profile.ProfileViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as  T
            }
            modelClass.isAssignableFrom(OnBoardingViewModel::class.java) -> {
                OnBoardingViewModel(pref) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            modelClass.isAssignableFrom(PointViewModel::class.java) -> {
                PointViewModel(pref) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(pref) as T
            }
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("unknow viewmodel class" + modelClass.name)
        }
    }
}