package com.miso.vinilos.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.miso.vinilos.R
import com.miso.vinilos.landing.LandingActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnUser = findViewById<Button>(R.id.btnUsuario)
        val btnAdmin = findViewById<Button>(R.id.btnAdmin)

        btnUser.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            intent.putExtra("isAdmin", false)
            startActivity(intent)
            finish()
        }

        btnAdmin.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            intent.putExtra("isAdmin", true)
            startActivity(intent)
            finish()
        }
    }
}