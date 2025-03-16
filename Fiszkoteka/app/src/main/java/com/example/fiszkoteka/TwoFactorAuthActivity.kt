package com.example.fiszkoteka

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts

class TwoFactorAuthActivity : AppCompatActivity() {

    private lateinit var verificationCodeEditText: TextInputEditText
    private lateinit var verificationCodeInput: TextInputLayout
    private lateinit var verifyButton: Button
    private lateinit var resendCodeText: TextView
    private var userEmail: String = ""

    private val CHANNEL_ID = "auth_notification_channel"
    private val NOTIFICATION_ID = 1001
    private val TAG = "TwoFactorAuth"
    private val PERMISSION_REQUEST_CODE = 100

    // Request permission launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "Notification permission granted by user")
            showNotificationSafely()
            navigateToMainActivity()
        } else {
            Log.d(TAG, "Notification permission denied by user")
            // Navigate anyway if permission denied
            navigateToMainActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_factor_auth)

        createNotificationChannel()
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""

        // Find views
        verificationCodeInput = findViewById(R.id.verificationCodeInput)
        verificationCodeEditText = verificationCodeInput.editText as TextInputEditText
        verifyButton = findViewById(R.id.verifyButton)
        resendCodeText = findViewById(R.id.resendCodeText)

        verifyButton.setOnClickListener {
            // Clear previous error
            verificationCodeInput.error = null
            val code = verificationCodeEditText.text.toString().trim()

            if (code.isEmpty()) {
                verificationCodeInput.error = "Pole kodu weryfikacyjnego nie może być puste"
            } else if (code.length != 6 || !code.all { it.isDigit() }) {
                verificationCodeInput.error = "Kod musi składać się z 6 cyfr"
            } else {
                Toast.makeText(this, "Weryfikacja udana", Toast.LENGTH_SHORT).show()
                checkAndRequestNotificationPermission()
            }
        }

        resendCodeText.setOnClickListener {
            Toast.makeText(this, "Wysłano nowy kod weryfikacyjny na adres $userEmail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Already has permission
                    Log.d(TAG, "Already has notification permission")
                    showNotificationSafely()
                    navigateToMainActivity()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Show rationale if needed
                    Log.d(TAG, "Showing permission rationale")
                    Toast.makeText(
                        this,
                        "Powiadomienia pozwalają na wyświetlenie informacji o weryfikacji",
                        Toast.LENGTH_LONG
                    ).show()
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    // Request permission
                    Log.d(TAG, "Requesting notification permission")
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Permission not required for Android < 13
            showNotificationSafely()
            navigateToMainActivity()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Powiadomienia autoryzacji"
            val descriptionText = "Powiadomienia o procesie logowania"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableLights(true)
                enableVibration(true)
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d(TAG, "Notification channel created: $CHANNEL_ID")
        }
    }

    private fun showNotificationSafely() {
        try {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntentFlags)

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Weryfikacja udana")
                .setContentText("Pomyślnie zweryfikowano i zalogowano użytkownika $userEmail")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            // Check permission before sending notification
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
                Log.d(TAG, "Notification sent with ID: $NOTIFICATION_ID")
            } else {
                Log.d(TAG, "Cannot show notification - no permission")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error showing notification", e)
        }
    }

    private fun navigateToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 500)
    }
}