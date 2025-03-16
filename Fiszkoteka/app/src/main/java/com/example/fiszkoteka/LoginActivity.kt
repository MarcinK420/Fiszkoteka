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
        val emailEditText = emailInputLayout.editText as TextInputEditText?
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpText = findViewById<TextView>(R.id.signUpText)

        loginButton.setOnClickListener {
            val email = emailEditText?.text.toString()

            if (email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Email is valid, proceed to 2FA
                Toast.makeText(this, "Wysyłanie kodu weryfikacyjnego...", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TwoFactorAuthActivity::class.java)
                intent.putExtra("USER_EMAIL", email)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Wprowadź prawidłowy adres email", Toast.LENGTH_SHORT).show()
            }
        }

        signUpText.setOnClickListener {
            // Handle sign up logic here
            Toast.makeText(this, "Funkcja rejestracji będzie dostępna wkrótce", Toast.LENGTH_SHORT).show()
        }
    }
}