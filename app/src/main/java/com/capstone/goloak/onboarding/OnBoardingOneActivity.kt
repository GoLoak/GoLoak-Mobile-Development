package com.capstone.goloak.onboarding

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.databinding.ActivityOnBoardingOneBinding
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