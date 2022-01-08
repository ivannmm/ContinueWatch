package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Threads : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private val SECONDS = "seconds"
    private val APP = "ContinueWatch"
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var thread: Thread


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(APP, MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStop() {
        with(sharedPreferences.edit()) {
            putInt(SECONDS, secondsElapsed)
            apply()
        }
        thread.interrupt()
        super.onStop()
    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(SECONDS, secondsElapsed)
        thread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = ("Seconds elapsed:" + secondsElapsed++)
                    }
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }
        thread.start()
        super.onStart()
    }
}