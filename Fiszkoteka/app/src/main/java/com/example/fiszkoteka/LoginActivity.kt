package com.example.fiszkoteka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailInputLayout = findViewById<TextInputLayout>(R.id.emailInput)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInput)
        val emailEditText = emailInputLayout.editText as TextInputEditText?
        val passwordEditText = passwordInputLayout.editText as TextInputEditText?
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpText = findViewById<TextView>(R.id.signUpText)

        loginButton.setOnClickListener {
            // Clear previous error messages
            emailInputLayout.error = null
            passwordInputLayout.error = null

            val email = emailEditText?.text.toString().trim()
            val password = passwordEditText?.text.toString().trim()

            var isValid = true

            if (email.isEmpty()) {
                emailInputLayout.error = "Pole email nie może być puste"
                isValid = false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInputLayout.error = "Wprowadź prawidłowy adres email"
                isValid = false
            }

            if (password.isEmpty()) {
                passwordInputLayout.error = "Pole hasło nie może być puste"
                isValid = false
            }

            if (isValid) {
                // Email and password are valid, proceed to 2FA
                Toast.makeText(this, "Wysyłanie kodu weryfikacyjnego...", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TwoFactorAuthActivity::class.java)
                intent.putExtra("USER_EMAIL", email)
                startActivity(intent)
            }
        }

        signUpText.setOnClickListener {
            // Handle sign up logic here
            Toast.makeText(this, "Funkcja rejestracji będzie dostępna wkrótce", Toast.LENGTH_SHORT).show()
        }
    }
}