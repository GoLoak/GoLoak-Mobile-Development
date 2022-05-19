package com.capstone.goloak.onboarding

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.capstone.goloak.MainActivity
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ActivityOnBoardingThreeBinding
import com.capstone.goloak.ui.login.LoginActivity

class OnBoardingThreeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOnBoardingThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStart.setOnClickListener(this@OnBoardingThreeActivity)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}