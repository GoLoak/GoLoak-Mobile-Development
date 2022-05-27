package com.capstone.goloak.ui.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.R
import com.capstone.goloak.databinding.ActivityRegisterBinding
import com.capstone.goloak.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private var binding : ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        hideSystemUI()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]
        mainViewModel.message.observe(this, {message ->
            showToast(message.toString())
        })

        mainViewModel.loading.observe(this, {loading ->
            isLoading(loading)
        })

        binding!!.imgBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding!!.btnRegister.setOnClickListener{
            val email = binding!!.edtEmail.text.toString().trim()
            val password= binding!!.edtPassword.text.toString().trim()
            val fullname = binding!!.edtFullName.text.toString().trim()
            val address = binding!!.edtAddress.text.toString().trim()
            val phone_number = binding!!.edtNumberphone.text.toString().trim()

            when{
                fullname.isEmpty() ->{
                    binding!!.edtFullName.error = "isi ini dulu"
                }
                password.isEmpty() ->{
                    binding!!.edtPassword.error = "isi ini dulu"
                }
                password.length < 6 && password.isNotEmpty() ->{
                    binding!!.edtPassword.error = "tidak boleh kurang dari 6 karakter"
                }
                email.isEmpty() ->{
                    binding!!.edtEmail.error = "isi ini dulu"
                }
                phone_number.isEmpty() ->{
                    binding!!.edtNumberphone.error = "isi ini dulu"
                }
                address.isEmpty() ->{
                    binding!!.edtAddress.error = "isi ini dulu"
                }

                else -> {
                    mainViewModel.register(fullname, password, email, phone_number, address)
                }
            }
        }
    }

    fun showToast(message:  String){
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }

    fun registerStatus(status: Boolean){
        if (!status){
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun isLoading(loading: Boolean){
        if (loading){
            binding!!.progressbar.visibility = View.VISIBLE
        }else{
            binding!!.progressbar.visibility = View.GONE
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