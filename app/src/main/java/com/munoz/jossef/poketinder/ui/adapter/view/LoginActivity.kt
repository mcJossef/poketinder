package com.munoz.jossef.poketinder.ui.adapter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import com.munoz.jossef.poketinder.ui.adapter.viewmodel.LoginViewModel
import com.munoz.jossef.poketinder.databinding.ActivityLoginBinding
import org.json.JSONArray

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = LoginViewModel(this)
        observeValues()
    }

    private fun observeValues() {
        loginViewModel.inputsError.observe(this) {
            Toast.makeText(this, "Ingrese los datos completos o correctos", Toast.LENGTH_SHORT).show()
        }
        loginViewModel.emailFormatError.observe(this) {
            Toast.makeText(this, "Por favor, ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show()
        }
        loginViewModel.authError.observe(this) {
            Toast.makeText(this, "Error usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
        loginViewModel.loginSuccess.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            val validationStatus = validateCredentials(email, password)
            when (validationStatus) {
                0 -> {
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
                    loginViewModel.validateInputs(email, password)
                    startActivity(Intent(this, MainActivity::class.java))
                }
                1 -> {
                    Toast.makeText(this, "Tu contraseña está incorrecta.", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "tu correo o contraseña es incorrecto , si no es asi registrate.", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun validateCredentials(email: String, password: String): Int {
        val sharedPreferences = getSharedPreferences("MY_SETTINGS_REGISTER", MODE_PRIVATE)
        val usersJson = sharedPreferences.getString("users", "[]")
        val usersArray = JSONArray(usersJson)

        var emailFound = false
        for (i in 0 until usersArray.length()) {
            val user = usersArray.getJSONObject(i)
            if (user.getString("email") == email) {
                emailFound = true
                if (user.getString("password") == password) {
                    return 0
                }
            }
        }
        return if (emailFound) 1 else 2
    }
}
