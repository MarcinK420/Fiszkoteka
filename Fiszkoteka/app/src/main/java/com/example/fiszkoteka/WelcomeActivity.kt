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
// W metodzie onClick przycisku rozpocznij:
        getStartedButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java) // Zmiana z MainActivity na LoginActivity
            startActivity(intent)
            finish()
        }
    }
}