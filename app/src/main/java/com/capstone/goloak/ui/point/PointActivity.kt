package com.capstone.goloak.ui.point

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ActivityHistoryBinding
import com.capstone.goloak.databinding.ActivityPointBinding

class PointActivity : AppCompatActivity() {
    private var binding : ActivityPointBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)

        binding?.imgBack?.setOnClickListener {
            onBackPressed()
        }
    }
}