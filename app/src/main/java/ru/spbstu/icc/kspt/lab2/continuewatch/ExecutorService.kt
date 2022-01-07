package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
        Thread.currentThread().name = "Thread" + Thread.currentThread().id
        Log.d(APP, "Created thread " + Thread.currentThread().name)
        while (!Thread.currentThread().isInterrupted) {
            try {
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.text, secondsElapsed++)
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                Log.d(APP, "Interrupted thread")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(APP, MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStop() {
        with(sharedPreferences.edit()) {
            putInt(SECONDS, secondsElapsed) // передаем ключ и значение,которое хоти записать
            apply() // сохраняем его
        }
        future.cancel(true)
        Log.d(APP, "Activity stopped")
        super.onStop()

    }

    override fun onStart() {
        secondsElapsed = sharedPreferences.getInt(SECONDS, secondsElapsed)
        future = service.submit(backgroundThread)
        Log.d(APP, "Activity started")
        super.onStart()
    }
}