package com.miso.vinilos.landing

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.miso.vinilos.R
import com.miso.vinilos.main.MainActivity

class LandingActivity : AppCompatActivity() {

    private lateinit var actButton: FloatingActionButton
    private lateinit var backToMainBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing)

        actButton = findViewById(R.id.act)
        backToMainBtn = findViewById(R.id.btnBackToMain)

        validateAdmin()
        bindButtonActions()
    }

    private fun validateAdmin() {
        val isAdmin = intent.getBooleanExtra("isAdmin", false)
        if (isAdmin) {
            actButton.show()
            actButton.setOnClickListener {
                // Acción para admin
            }
        } else {
            actButton.hide()
        }
    }

    private fun bindButtonActions() {
        bindBackToMain()
    }

    private fun bindBackToMain() {
        backToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}