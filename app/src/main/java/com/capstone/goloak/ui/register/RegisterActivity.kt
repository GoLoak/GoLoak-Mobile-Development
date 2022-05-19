package com.capstone.goloak.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            imgBack.setOnClickListener(this@RegisterActivity)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.img_back -> {
                onBackPressed()
            }
        }
    }
}