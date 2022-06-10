package com.capstone.goloak.onboarding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.databinding.ActivityOnBoardingThreeBinding
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class OnBoardingThreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = SettingPreferences.getInstance(dataStore)
        val obViewModel = ViewModelProvider(this, ViewModelFactory(pref))[OnBoardingViewModel::class.java]

        binding = ActivityOnBoardingThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStart.setOnClickListener {
                obViewModel.saveSesi(true)
                val intent = Intent(this@OnBoardingThreeActivity, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}