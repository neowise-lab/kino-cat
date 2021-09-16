package com.neowise.kinocat.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neowise.kinocat.R
import com.neowise.kinocat.databinding.ActivitySplashBinding
import java.util.*

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sleep 1500ms, after show activity
        Timer().schedule(object : TimerTask() {
            override fun run() {
                showMainActivity()
                finish()
            }
        }, 2000)
    }

    fun showMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}