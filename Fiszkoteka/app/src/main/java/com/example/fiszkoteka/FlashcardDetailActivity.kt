package com.example.fiszkoteka

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class FlashcardDetailActivity : AppCompatActivity() {

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var flashcardView: CardView
    private var isShowingFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard_detail)

        // Get flashcard data from intent
        val front = intent.getStringExtra("FLASHCARD_FRONT") ?: ""
        val back = intent.getStringExtra("FLASHCARD_BACK") ?: ""

        // Initialize views
        viewFlipper = findViewById(R.id.viewFlipper)
        flashcardView = findViewById(R.id.flashcardView)
        val frontTextView = findViewById<TextView>(R.id.frontTextView)
        val backTextView = findViewById<TextView>(R.id.backTextView)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        // Set flashcard data
        frontTextView.text = front
        backTextView.text = back

        // Setup toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Setup flashcard flip
        flashcardView.setOnClickListener {
            flipCard()
        }
    }

    private fun flipCard() {
        val inAnimation = if (isShowingFront)
            AnimationUtils.loadAnimation(this, R.anim.flip_in_back)
        else
            AnimationUtils.loadAnimation(this, R.anim.flip_in_front)

        val outAnimation = if (isShowingFront)
            AnimationUtils.loadAnimation(this, R.anim.flip_out_front)
        else
            AnimationUtils.loadAnimation(this, R.anim.flip_out_back)

        viewFlipper.inAnimation = inAnimation
        viewFlipper.outAnimation = outAnimation
        viewFlipper.showNext()
        isShowingFront = !isShowingFront
    }
}