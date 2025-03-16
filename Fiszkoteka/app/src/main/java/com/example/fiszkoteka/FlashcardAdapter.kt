package com.example.fiszkoteka

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlashcardAdapter(
    private val flashcards: List<Flashcard>,
    private val onFlashcardClicked: (Flashcard) -> Unit
) : RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    class FlashcardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frontText: TextView = view.findViewById(R.id.flashcardFrontText)
        val backText: TextView = view.findViewById(R.id.flashcardBackText)
        val cardContainer: View = view.findViewById(R.id.flashcardContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard, parent, false)
        return FlashcardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        val flashcard = flashcards[position]
        holder.frontText.text = flashcard.front
        holder.backText.text = flashcard.back

        // Make back text invisible initially
        holder.backText.visibility = View.GONE
        holder.frontText.visibility = View.VISIBLE

        holder.cardContainer.setOnClickListener {
            onFlashcardClicked(flashcard)
        }
    }

    override fun getItemCount() = flashcards.size
}

data class Flashcard(val front: String, val back: String)