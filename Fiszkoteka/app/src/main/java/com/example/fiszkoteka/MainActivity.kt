package com.example.fiszkoteka

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.flashcardsRecyclerView)

        // Example flashcards
        val exampleFlashcards = listOf(
            Flashcard("Hello", "Cześć"),
            Flashcard("Goodbye", "Do widzenia"),
            Flashcard("Thank you", "Dziękuję"),
            Flashcard("Please", "Proszę"),
            Flashcard("Good morning", "Dzień dobry")
        )

        recyclerView.adapter = FlashcardAdapter(exampleFlashcards)

        val fab = findViewById<FloatingActionButton>(R.id.addFlashcardFab)
        fab.setOnClickListener {
            showAddFlashcardDialog()
        }
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already on home screen
                    true
                }
                R.id.nav_learn -> {
                    Toast.makeText(this, "Ekran nauki (w przygotowaniu)", Toast.LENGTH_SHORT).show()
                    true
                }
// Replace the settings navigation code in MainActivity.kt
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun showAddFlashcardDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_flashcard, null)

        val frontInput = dialogView.findViewById<TextInputEditText>(R.id.frontInput)
        val backInput = dialogView.findViewById<TextInputEditText>(R.id.backInput)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        saveButton.setOnClickListener {
            val front = frontInput.text.toString()
            val back = backInput.text.toString()

            if (front.isEmpty() || back.isEmpty()) {
                Toast.makeText(this, "Wypełnij oba pola", Toast.LENGTH_SHORT).show()
            } else {
                // Here you would add the new flashcard to your list
                // For now, just show a success message
                Toast.makeText(this, "Fiszka dodana (funkcjonalność w przygotowaniu)", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}