package com.example.fiszkoteka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TwoFactorAuthActivity : AppCompatActivity() {

    private lateinit var verificationCodeEditText: TextInputEditText
    private lateinit var verificationCodeInput: TextInputLayout
    private lateinit var verifyButton: Button
    private lateinit var resendCodeText: TextView
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_factor_auth)

        // Get the email from the intent extra
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""

        // Find views
        verificationCodeInput = findViewById(R.id.verificationCodeInput)
        verificationCodeEditText = findViewById(R.id.verificationCodeEditText)
        verifyButton = findViewById(R.id.verifyButton)
        resendCodeText = findViewById(R.id.resendCodeText)

        // Setup verify button click listener
        verifyButton.setOnClickListener {
            // Clear previous error
            verificationCodeInput.error = null

            val code = verificationCodeEditText.text.toString().trim()

            if (code.isEmpty()) {
                verificationCodeInput.error = "Pole kodu weryfikacyjnego nie może być puste"
            } else if (code.length != 6 || !code.all { it.isDigit() }) {
                verificationCodeInput.error = "Kod musi składać się z 6 cyfr"
            } else {
                // Code is valid, proceed to MainActivity
                Toast.makeText(this, "Weryfikacja udana", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Setup resend code text click listener
        resendCodeText.setOnClickListener {
            // In a real app, you would resend the code to the user's email
            Toast.makeText(this, "Wysłano nowy kod weryfikacyjny na adres $userEmail", Toast.LENGTH_SHORT).show()
        }
    }
}