package com.example.androiddb.Presentation.login
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.androiddb.MainState
import com.example.androiddb.Presentation.home.HomeActivity

import com.example.androiddb.ViewModels.login.LoginViewModel
import com.example.androiddb.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val email = binding.User.text.toString()
            val password = binding.Password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.performLogin(email, password)
            }
        }

        // ✅ Observar cambios del ViewModel
        lifecycleScope.launch {
            loginViewModel.mainState.collect { state ->
                when (state) {
                    is MainState.LoginSuccessfull -> {
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("TOKEN", state.token)
                        startActivity(intent)
                    }
                    is MainState.Loading -> {
                        Log.d("LoginActivity", "Estado Loading: sin acción")

                    }

                    is MainState.HeroDownloaded -> {
                        Log.d("HerosDowloand", "Estado descarga: Todavía sin acción")
                    }

                    is MainState.Idle -> {
                        Log.d("LoginActivity", "Estado Idle: sin acción")
                    }

                    is MainState.Error -> {
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()
                    }


                }

            }
        }
    }
}
