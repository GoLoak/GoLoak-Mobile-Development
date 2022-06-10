package com.capstone.goloak.onboarding

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ActivityOnBoardingTwoBinding

class OnBoardingTwoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityOnBoardingTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnNext.setOnClickListener(this@OnBoardingTwoActivity)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_next -> {
                val intent = Intent(this, OnBoardingThreeActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(v.context as Activity).toBundle())
            }
        }
    }
}