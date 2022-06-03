package com.capstone.goloak.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.goloak.R
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.adapter.HomeAdapter
import com.capstone.goloak.databinding.FragmentHomeBinding
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.helper.withNumberingFormat
import com.capstone.goloak.ui.history.HistoryActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
//        val homeViewModel = ViewModelProvider(this, ViewModelFactory(pref))[HomeViewModel::class.java]
        showRecyclerView()

        binding.imgHistory.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)
            startActivity(intent)
        }

        getHomeViewModel().loading.observe(viewLifecycleOwner) {
            isLoading(it)
        }

        getHomeViewModel().dataFullName.observe(viewLifecycleOwner) {
            if (it != null) binding.fullName.text = it
        }

        getHomeViewModel().dataPoint.observe(viewLifecycleOwner) {
            if (it != null) binding.totalPoint.text = getString(R.string.total_point, it.toString().withNumberingFormat())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRecyclerView() {
        val adapter = HomeAdapter()

        binding.apply {
            rvListSampah.layoutManager = LinearLayoutManager(activity)
            rvListSampah.adapter = adapter
        }

        getHomeViewModel().getId().observe(viewLifecycleOwner) { userId ->
            getHomeViewModel().getUserHome(userId)
            getHomeViewModel().dataListTrash.observe(viewLifecycleOwner) { list ->
                adapter.submitList(list)
            }
        }
    }

    private fun getHomeViewModel(): HomeViewModel {
        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        return ViewModelProvider(this, ViewModelFactory(pref))[HomeViewModel::class.java]
    }

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                fullName.text = getString(R.string.loading)
                totalPoint.text = getString(R.string.loading)
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }

        }
    }
}