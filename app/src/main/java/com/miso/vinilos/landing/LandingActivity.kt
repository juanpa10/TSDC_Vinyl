package com.miso.vinilos.landing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.miso.vinilos.R
import com.miso.vinilos.main.MainActivity
import com.miso.vinilos.ui.listactivities.AlbumListActivity
import com.miso.vinilos.ui.listactivities.BandListActivity

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
        bindBandListBtn()
        bindAlbumListBtn()
    }

    private fun bindBackToMain() {
        backToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun bindBandListBtn() {
        findViewById<ImageView>(R.id.imgArtistas).setOnClickListener {
            val intent = Intent(this, BandListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindAlbumListBtn() {

        findViewById<ImageView>(R.id.imgAlbumes).setOnClickListener {
            println("boton a getAlbums");
            val intent = Intent(this, AlbumListActivity::class.java)
            startActivity(intent)
        }
    }
}