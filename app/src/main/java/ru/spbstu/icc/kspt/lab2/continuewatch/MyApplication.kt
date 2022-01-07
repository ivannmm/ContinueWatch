package ru.spbstu.icc.kspt.lab2.continuewatch

import android.app.Application
import java.util.concurrent.Executors

class MyApplication: Application() {
    val executor = Executors.newSingleThreadExecutor()
}