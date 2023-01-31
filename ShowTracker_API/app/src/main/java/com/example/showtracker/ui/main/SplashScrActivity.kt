package com.example.showtracker.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showtracker.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScrActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scr)
        supportActionBar?.hide()

        val activityScope = CoroutineScope(Dispatchers.Main)
        activityScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashScrActivity, MainActivity::class.java))
            finish()
        }
    }
}