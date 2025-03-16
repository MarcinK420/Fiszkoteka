package com.example.fiszkoteka

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            Toast.makeText(this, "Dodaj nową fiszkę", Toast.LENGTH_SHORT).show()
            // Add code to create new flashcard
        }
    }
}