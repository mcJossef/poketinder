package com.munoz.jossef.poketinder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.munoz.jossef.poketinder.databinding.ActivityLoginBinding
import com.munoz.jossef.poketinder.databinding.ActivityRegisterBinding
import org.json.JSONArray
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerViewModel = RegisterViewModel(this)
        observeValues()
    }

    private fun observeValues() {
        registerViewModel.inputsError.observe(this) {
            Toast.makeText(this, "Ingrese los datos completos", Toast.LENGTH_SHORT).show()
        }
        registerViewModel.emailFormatError.observe(this) {
            Toast.makeText(this, "Por favor, ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show()
        }
        registerViewModel.authError.observe(this) {
            Toast.makeText(this, "Error usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
        registerViewModel.registerSuccess.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        registerViewModel.passwordsMismatchError.observe(this) {
            Toast.makeText(this, "Las contraseñas no coinciden. Por favor, inténtelo de nuevo.", Toast.LENGTH_SHORT).show()
        }
        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtPassword2.text.toString()

            if (!isValidEmail(email)) {
                Toast.makeText(this, "El formato del correo electrónico es incorrecto.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!passwordsMatch(password, confirmPassword)) {
                Toast.makeText(this, "Las contraseñas no coinciden. Por favor, inténtelo de nuevo.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!userExists(email)) {
                val newUser = JSONObject()
                newUser.put("email", email)
                newUser.put("password", password)

                saveUser(newUser)

                registerViewModel.validateInputs(
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Ya existe un usuario registrado con ese correo electrónico.", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnRegresar.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnBackClose.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun passwordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
    private fun userExists(email: String): Boolean {
        val usersJson = getSharedPreferences("MY_SETTINGS_REGISTER", MODE_PRIVATE).getString("users", "[]")
        val usersArray = JSONArray(usersJson)
        for (i in 0 until usersArray.length()) {
            if (usersArray.getJSONObject(i).getString("email") == email) {
                return true
            }
        }
        return false
    }

    private fun saveUser(user: JSONObject) {
        val sharedPreferences = getSharedPreferences("MY_SETTINGS_REGISTER", MODE_PRIVATE)
        val usersJson = sharedPreferences.getString("users", "[]")
        val usersArray = JSONArray(usersJson)
        usersArray.put(user)
        sharedPreferences.edit().putString("users", usersArray.toString()).apply()
    }
}