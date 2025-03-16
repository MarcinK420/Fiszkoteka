package com.example.fiszkoteka

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupToolbar()
        setupBottomNavigation()
        setupListeners()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        // No back button needed since we use bottom navigation
    }

    private fun setupBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // Select the settings tab
        bottomNavigation.selectedItemId = R.id.nav_settings

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Close current activity to avoid stacking
                    true
                }
                R.id.nav_learn -> {
                    Toast.makeText(this, "Ekran nauki (w przygotowaniu)", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_settings -> {
                    // Already on settings screen
                    true
                }
                else -> false
            }
        }
    }

    private fun setupListeners() {
        // Theme toggle
        findViewById<SwitchMaterial>(R.id.themeSwitch).setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Włączono tryb ciemny" else "Włączono tryb jasny"
            Toast.makeText(this, "$message (funkcjonalność w przygotowaniu)", Toast.LENGTH_SHORT).show()
        }

        // Notification toggle
        findViewById<SwitchMaterial>(R.id.notificationSwitch).setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Powiadomienia włączone" else "Powiadomienia wyłączone"
            Toast.makeText(this, "$message (funkcjonalność w przygotowaniu)", Toast.LENGTH_SHORT).show()
        }

        // Account settings
        findViewById<android.view.View>(R.id.accountSettings).setOnClickListener {
            Toast.makeText(this, "Zarządzanie kontem (funkcjonalność w przygotowaniu)", Toast.LENGTH_SHORT).show()
        }

        // Logout button
        findViewById<android.view.View>(R.id.logoutButton).setOnClickListener {
            Toast.makeText(this, "Wylogowywanie (funkcjonalność w przygotowaniu)", Toast.LENGTH_SHORT).show()
        }

        // About app
        findViewById<android.view.View>(R.id.aboutAppButton).setOnClickListener {
            Toast.makeText(this, "Fiszkoteka - aplikacja do nauki ze słówek", Toast.LENGTH_SHORT).show()
        }
    }
}