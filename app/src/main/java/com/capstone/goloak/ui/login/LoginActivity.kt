package com.capstone.goloak.ui.login

import android.content.Context
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
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.goloak.MainActivity
import com.capstone.goloak.R
import com.capstone.goloak.ViewModelFactory
import com.capstone.goloak.databinding.ActivityLoginBinding
import com.capstone.goloak.datastore.SettingPreferences
import com.capstone.goloak.ui.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    private var binding : ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        hideSystemUI()

        val registerMsg = intent.getStringExtra(EXTRA_RESULT)
        if (registerMsg != null) snackBar(registerMsg)

        //datastore
        val pref = SettingPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref))[LoginViewModel::class.java]

        loginViewModel.message.observe(this) { message ->
            snackBar(message)
        }
        loginViewModel.loading.observe(this) { loading ->
            isLoading(loading)
        }

        loginViewModel.saveUser.observe(this) {
            if (it) {
                loginViewModel.saveSesi(it)
                loginViewModel.dataToken.observe(this) { token ->
                    loginViewModel.saveToken(token.toString())
                }
                loginViewModel.dataId.observe(this) { id ->
                    loginViewModel.saveIdUser(id.toString())
                }
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_RESULT, loginViewModel.message.value)
                startActivity(intent)
                finish()
            }
        }

        binding?.btnRegister?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding?.btnLogin?.setOnClickListener{
            val email = binding?.edtEmail?.text.toString().trim()
            val password = binding?.edtPassword?.text.toString().trim()

            when{
                email.isEmpty() -> {
                    binding?.edtEmail?.error = "isi dulu"
                    binding?.edtEmail?.requestFocus()
                }
                !isValidEmail(email) -> {
                    binding?.edtEmail?.error = "Email tidak sah"
                    binding?.edtEmail?.requestFocus()
                }
                password.isEmpty() -> {
                    binding?.edtPassword?.error = "isi dulu"
                    binding?.edtPassword?.requestFocus()
                }
                password.length < 6 && password.isNotEmpty() -> {
                    binding?.edtPassword?.error = "tidak boleh kurang dari 6 karakter"
                    binding?.edtPassword?.requestFocus()
                }
                else ->{
                     loginViewModel.login(email, password)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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
            isEnableButton(binding?.btnLogin, false)
            binding?.progressbar?.visibility = View.VISIBLE
        } else {
            isEnableButton(binding?.btnLogin, true)
            binding?.progressbar?.visibility = View.GONE
        }
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
        const val EXTRA_RESULT = "extra_result"
    }
}