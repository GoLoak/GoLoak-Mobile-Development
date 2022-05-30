package com.capstone.goloak.ui.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ActivityRegisterBinding
import com.capstone.goloak.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private var binding : ActivityRegisterBinding? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        hideSystemUI()

        val registerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]
        registerViewModel.message.observe(this) { message ->
            snackBar(message)
        }

        registerViewModel.error.observe(this) {
            if (it == false) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra(LoginActivity.EXTRA_RESULT, registerViewModel.message.value)
                startActivity(intent)
                finish()
            }
        }

        registerViewModel.loading.observe(this) { loading ->
            isLoading(loading)
        }

        binding?.imgBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.btnRegister?.setOnClickListener{
            val email = binding?.edtEmail?.text.toString().trim()
            val password= binding?.edtPassword?.text.toString().trim()
            val fullName = binding?.edtFullName?.text.toString().trim()
            val address = binding?.edtAddress?.text.toString().trim()
            val phoneNumber = binding?.edtNumberphone?.text.toString().trim()

            when{
                email.isEmpty() ->{
                    binding?.edtEmail?.error = "Email tidak boleh kosong"
                }
                !isValidEmail(email) ->{
                    binding?.edtEmail?.error = "Email tidak sah"
                }
                password.isEmpty() ->{
                    binding?.edtPassword?.error = "Password tidak boleh kosong"
                }
                fullName.isEmpty() ->{
                    binding?.edtFullName?.error = "Nama lengkap tidak boleh kosong"
                }
                address.isEmpty() ->{
                    binding?.edtAddress?.error = "Alamat tidak boleh kosong"
                }
                phoneNumber.isEmpty() ->{
                    binding?.edtNumberphone?.error = "Nomor telepon tidak boleh kosong"
                }
                password.length < 6 && password.isNotEmpty() ->{
                    binding?.edtPassword?.error = "Password tidak boleh kurang dari 6 karakter"
                } else -> {
                registerViewModel.register(fullName, password, email, phoneNumber, address)
                }
            }
        }
    }

    private fun snackBar(message: String){
        Snackbar.make(
            window.decorView.rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun isEnableButton(button: Button?, isEnabled: Boolean) {
        if (!isEnabled) {
            button?.isEnabled = isEnabled
            button?.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
            button?.background = ContextCompat.getDrawable(applicationContext, R.drawable.shape_rectangle_disabled)
        } else {
            button?.isEnabled = isEnabled
            button?.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            button?.background = ContextCompat.getDrawable(applicationContext, R.drawable.shape_rectangle_green)
        }
    }

    private fun isLoading(loading: Boolean){
        if (loading) {
            isEnableButton(binding?.btnRegister, false)
            binding?.progressbar?.visibility = View.VISIBLE
        } else {
            isEnableButton(binding?.btnRegister, true)
            binding?.progressbar?.visibility = View.GONE
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}