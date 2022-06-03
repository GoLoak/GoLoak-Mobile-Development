package com.capstone.goloak.ui.history

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
import com.capstone.goloak.adapter.HistoryAdapter
import com.capstone.goloak.databinding.ActivityHistoryBinding
import com.capstone.goloak.datastore.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HistoryActivity : AppCompatActivity() {
    private var binding : ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        showRecyclerView()

        binding?.imgBack?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showRecyclerView() {
        val pref = SettingPreferences.getInstance(dataStore)
        val historyViewModel = ViewModelProvider(this, ViewModelFactory(pref))[HistoryViewModel::class.java]

        val adapter = HistoryAdapter()

        binding?.apply {
            rvHistory.layoutManager = LinearLayoutManager(applicationContext)
            rvHistory.adapter = adapter
        }

        historyViewModel.loading.observe(this) {
            isLoading(it)
        }

        historyViewModel.getId().observe(this) { userId ->
            historyViewModel.getListHistory(userId)
            historyViewModel.dataListHistory.observe(this) {
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