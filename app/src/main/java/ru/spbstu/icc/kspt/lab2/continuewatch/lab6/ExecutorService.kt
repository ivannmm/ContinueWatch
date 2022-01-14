package ru.spbstu.icc.kspt.lab2.continuewatch.lab6

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.icc.kspt.lab2.continuewatch.R
import java.util.concurrent.Future

class ExecutorService : AppCompatActivity(){
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private val SECONDS = "seconds"
    private val APP = "ContinueWatch"
    lateinit var sharedPreferences: SharedPreferences
    private val service = MyApplication().executor
    private lateinit var future: Future<*>

    private val backgroundThread = Runnable {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(APP, MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(SECONDS, secondsElapsed)
        future = service.submit(backgroundThread)
        super.onStart()
    }

    override fun onStop() {
        with(sharedPreferences.edit()) {
            putInt(SECONDS, secondsElapsed)
            apply()
        }
        future.cancel(true)
        super.onStop()

    }
}