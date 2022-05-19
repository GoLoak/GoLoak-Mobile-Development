package com.capstone.goloak.onboarding

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityOptionsCompat
import com.capstone.goloak.MainActivity
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ActivityOnBoardingOneBinding
import com.capstone.goloak.databinding.ActivitySplashScreenBinding
import com.capstone.goloak.ui.login.LoginActivity

class OnBoardingOneActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityOnBoardingOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnNext.setOnClickListener(this@OnBoardingOneActivity)
            btnSkip.setOnClickListener(this@OnBoardingOneActivity)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_next -> {
                val intent = Intent(this, OnBoardingTwoActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(v.context as Activity).toBundle())
            }
            R.id.btn_skip -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}