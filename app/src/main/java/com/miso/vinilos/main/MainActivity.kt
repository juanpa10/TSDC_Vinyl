package com.miso.vinilos.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.miso.vinilos.R
import com.miso.vinilos.landing.LandingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnUser: Button
    private lateinit var btnAdmin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnUser = findViewById(R.id.btnUsuario)
        btnAdmin = findViewById(R.id.btnAdmin)

        setButtonListeners()
    }

    private fun setButtonListeners() {
        btnUser.setOnClickListener { navigateToLanding(false) }
        btnAdmin.setOnClickListener { navigateToLanding(true) }
    }

    private fun navigateToLanding(isAdmin: Boolean) {
        val intent = Intent(this, LandingActivity::class.java).apply {
            putExtra("isAdmin", isAdmin)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }
}