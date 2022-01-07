package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity42 : AppCompatActivity(){
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private val SECONDS = "seconds"
    var collapsed = true;

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (collapsed) {
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
        backgroundThread.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(SECONDS, secondsElapsed)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            secondsElapsed = getInt(SECONDS)
        }
    }

    override fun onResume() {
        super.onResume()
        collapsed = true
    }

    override fun onStop() {
        super.onStop()
        collapsed = false
    }
}