package com.capstone.goloak

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.databinding.ActivitySplashScreenBinding
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.onboarding.OnBoardingOneActivity
import com.capstone.goloak.onboarding.OnBoardingViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val obViewModel = ViewModelProvider(this, ViewModelFactory(pref))[OnBoardingViewModel::class.java]

        Handler(Looper.getMainLooper()).postDelayed({
            obViewModel.getSesi().observe(this) {
                if (it == true) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    startActivity(Intent(this, OnBoardingOneActivity::class.java))
                    finish()
                }
            }
        }, 5000L)
    }
}