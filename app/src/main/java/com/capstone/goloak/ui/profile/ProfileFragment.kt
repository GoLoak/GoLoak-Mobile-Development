package com.capstone.goloak.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.MainViewModel
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.databinding.FragmentProfileBinding
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.ui.login.LoginActivity
import com.capstone.goloak.ui.point.PointActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]
        val profileViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ProfileViewModel::class.java]

        profileViewModel.loading.observe(viewLifecycleOwner) {
            isLoading(it)
        }
        profileViewModel.getId().observe(viewLifecycleOwner) { userId ->
            profileViewModel.getUserProfile(userId)
            profileViewModel.data.observe(viewLifecycleOwner) { profile ->
                if (profile != null) {
                    binding.apply {
                        edtFullname.setText(profile.fullName)
                        edtEmail.setText(profile.email)
                        edtAddress.setText(profile.address)
                        edtPhoneNumber.setText(profile.phoneNumber)
                    }
                }
            }
        }

        binding.apply {
            btnLogout.setOnClickListener {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Yakin ingin keluar?")
                    setMessage("Apakah Anda yakin ingin keluar dari akun Anda?")
                    setPositiveButton("Yakin") { _, _ ->
                        mainViewModel.logout()
                        ActivityCompat.finishAffinity(requireActivity())
                        val intent = Intent(activity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    setNegativeButton("Batal") { _, _ ->
                        setCancelable(true)
                    }
                    create()
                    show()
                }
            }
            btnPointHistory.setOnClickListener {
                startActivity(Intent(activity, PointActivity::class.java))
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isLoading(loading: Boolean) { binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE }
}