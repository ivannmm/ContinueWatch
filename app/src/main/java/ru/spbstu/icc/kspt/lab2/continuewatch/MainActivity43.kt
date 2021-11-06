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

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        super.onResume()
    }
}