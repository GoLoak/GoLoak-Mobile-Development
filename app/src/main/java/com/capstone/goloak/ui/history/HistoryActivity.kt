package com.capstone.goloak.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ActivityHistoryBinding
import com.capstone.goloak.databinding.ActivityRegisterBinding

class HistoryActivity : AppCompatActivity() {
    private var binding : ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        binding?.imgBack?.setOnClickListener {
            onBackPressed()
        }
    }
}