package com.example.to_do.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import com.example.to_do.ui.home.HomeActivity
import com.example.to_do.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startHomeActivity()
    }

    private fun startHomeActivity() {
        android.os.Handler(Looper.getMainLooper())
            .postDelayed({
                         val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            },2000)
    }
}