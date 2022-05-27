package com.capstone.goloak.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.MainActivity
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.databinding.ActivityLoginBinding
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.ui.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    private var binding : ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        hideSystemUI()

        //datastore
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[LoginViewModel::class.java]

        notifSuccesCreateAccounbt(EXTRA_STATUS)
        mainViewModel.loading.observe(this, {loading ->
            isLoading(loading)
        })

        mainViewModel.saveUser.observe(this, {errror ->
            statusLogin2(errror)
            if (!errror){
                mainViewModel.saveSessi(!errror)
                mainViewModel.data.observe(this, {token ->
                    mainViewModel.saveToken(token.toString())
                })
                move()
            }
        })

        binding!!.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding!!.btnLogin.setOnClickListener{
            val email = binding!!.edtEmail.text.toString().trim()
            val password = binding!!.edtPassword.text.toString().trim()

            when{
                email.isEmpty() ->{
                    binding!!.edtEmail.error = "isi dulu"
                } password.isEmpty() ->{
                binding!!.edtPassword.error = "isi dulu"
            }
                password.length < 6 && password.isNotEmpty() -> {
                    binding!!.edtPassword.error = "tidak boleh kurang dari 6 karakter"
                }
                else ->{
                     mainViewModel.login(email, password)
                }
            }
        }
    }


    private fun statusLogin2(status: Boolean){
        if (!status){
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    fun notifSuccesCreateAccounbt(status: String){
        if (status == "true"){
            binding!!.txtnotifsucces.visibility = View.VISIBLE
        }
    }

    fun isLoading(status: Boolean){
        if (status){
            binding!!.progressbar.visibility = View.VISIBLE
        }else{
            binding!!.progressbar.visibility = View.GONE
        }
    }

    fun move(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //for hide top bar
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


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object{
        const val EXTRA_STATUS = "false"
    }
}