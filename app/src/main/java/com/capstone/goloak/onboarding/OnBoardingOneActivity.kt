package com.capstone.goloak.onboarding

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.MainActivity
import com.capstone.goloak.R
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.databinding.ActivityOnBoardingOneBinding
import com.capstone.goloak.databinding.ActivitySplashScreenBinding
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class OnBoardingOneActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardingOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val obViewModel = ViewModelProvider(this, ViewModelFactory(pref))[OnBoardingViewModel::class.java]
        obViewModel.getSesi().observe(this) {
            if (it == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
        binding.apply {
            btnNext.setOnClickListener{
                val intent = Intent(this@OnBoardingOneActivity, OnBoardingTwoActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@OnBoardingOneActivity as Activity).toBundle())
            }
            btnSkip.setOnClickListener {
                obViewModel.saveSesi(true)
                val intent = Intent(this@OnBoardingOneActivity, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}