package com.munoz.jossef.poketinder

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Toast
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

            if (validateCredentials(email, password)) {
                loginViewModel.validateInputs(email, password)
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "No estás registrado, regístrate.", Toast.LENGTH_LONG).show()
            }
            //startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun validateCredentials(email: String, password: String): Boolean {
        val sharedPreferences = getSharedPreferences("MY_SETTINGS_REGISTER", MODE_PRIVATE)
        val usersJson = sharedPreferences.getString("users", "[]")
        val usersArray = JSONArray(usersJson)

        for (i in 0 until usersArray.length()) {
            val user = usersArray.getJSONObject(i)
            if (user.getString("email") == email && user.getString("password") == password) {
                return true
            }
        }
        return false
    }
}
