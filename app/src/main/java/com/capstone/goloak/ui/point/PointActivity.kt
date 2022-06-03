package com.capstone.goloak.ui.point

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.adapter.PointHistoryAdapter
import com.capstone.goloak.databinding.ActivityPointBinding
import com.capstone.goloak.datastore.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PointActivity : AppCompatActivity() {
    private var binding : ActivityPointBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        showRecyclerView()

        binding?.imgBack?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showRecyclerView() {
        val pref = SettingPreferences.getInstance(dataStore)
        val pointViewModel = ViewModelProvider(this, ViewModelFactory(pref))[PointViewModel::class.java]

        val adapter = PointHistoryAdapter()

        binding?.apply {
            rvPointHistory.layoutManager = LinearLayoutManager(applicationContext)
            rvPointHistory.adapter = adapter
        }

        pointViewModel.loading.observe(this) {
            isLoading(it)
        }

        pointViewModel.getId().observe(this) { userId ->
            pointViewModel.getPointHistory(userId)
            pointViewModel.data.observe(this) {
                if (it != null) {
                    if (it.isNotEmpty()) adapter.submitList(it) else binding?.tvNoData?.visibility = View.VISIBLE
                } else {
                    binding?.tvNoData?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) { binding?.progressBar?.visibility = if (loading) View.VISIBLE else View.GONE }
}