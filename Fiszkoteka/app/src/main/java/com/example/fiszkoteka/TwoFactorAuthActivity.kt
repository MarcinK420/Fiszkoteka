package com.example.fiszkoteka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class TwoFactorAuthActivity : AppCompatActivity() {

    private lateinit var verificationCodeEditText: TextInputEditText
    private lateinit var verifyButton: Button
    private lateinit var resendCodeText: TextView
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_factor_auth)

        // Get the email from the intent extra
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""

        // Find views
        verificationCodeEditText = findViewById(R.id.verificationCodeEditText)
        verifyButton = findViewById(R.id.verifyButton)
        resendCodeText = findViewById(R.id.resendCodeText)

        // Setup verify button click listener
        verifyButton.setOnClickListener {
            val code = verificationCodeEditText.text.toString()
            if (validateCode(code)) {
                // Code is valid, proceed to MainActivity
                Toast.makeText(this, "Weryfikacja udana", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Nieprawidłowy kod weryfikacyjny", Toast.LENGTH_SHORT).show()
            }
        }

        // Setup resend code text click listener
        resendCodeText.setOnClickListener {
            // In a real app, you would resend the code to the user's email
            Toast.makeText(this, "Wysłano nowy kod weryfikacyjny na adres $userEmail", Toast.LENGTH_SHORT).show()
        }

        // In a real app, you would actually send the verification code to the user's email here
    }

    private fun validateCode(code: String): Boolean {
        // For demonstration purposes, accept any 6-digit code
        // In a real app, you would validate against a code sent to the user's email
        return code.length == 6 && code.all { it.isDigit() }
    }
}