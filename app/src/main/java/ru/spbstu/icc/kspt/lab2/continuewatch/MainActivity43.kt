package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity43 : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private val SECONDS = "seconds"
    private val APP = "ContinueWatch"
    lateinit var sharedPreferences: SharedPreferences
    var collapsed = false

    var backgroundThread = Thread {
        while (true) {
            if (!collapsed) {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        sharedPreferences = getSharedPreferences(APP, MODE_PRIVATE)
        if (sharedPreferences.contains(SECONDS)) {
            secondsElapsed = sharedPreferences.getInt(SECONDS, 0)
        }
        backgroundThread.start()
    }

    override fun onPause(){
        sharedPreferences = getSharedPreferences(APP, MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()) {
            putInt(SECONDS, secondsElapsed)
            apply()
        }
        super.onPause()
    }

    override fun onResume() {
        sharedPreferences = getSharedPreferences(APP, MODE_PRIVATE) ?: return
        secondsElapsed = sharedPreferences.getInt(SECONDS, 0);
        collapsed = false
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        collapsed = true
    }
}