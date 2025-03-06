package com.example.fiszkoteka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val getStartedButton = findViewById<Button>(R.id.getStartedButton)
        getStartedButton.setOnClickListener {
            // Przejście do głównej aktywności
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Zakończenie bieżącej aktywności
        }
    }
}