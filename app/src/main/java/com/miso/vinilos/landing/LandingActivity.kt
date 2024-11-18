package com.miso.vinilos.landing

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.miso.vinilos.R
import com.miso.vinilos.ui.listactivities.AlbumListActivity
import com.miso.vinilos.ui.listactivities.BandListActivity
import com.miso.vinilos.ui.listactivities.CollectorListActivity

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

    override fun onDestroy() {
        super.onDestroy()
        actButton.setOnClickListener(null)
        backToMainBtn.setOnClickListener(null)
    }

    private fun validateAdmin() {
        val isAdmin = intent.getBooleanExtra("isAdmin", false)
        if (isAdmin) {
            actButton.show()
            actButton.setOnClickListener {
                Log.d("LandingActivity", "Admin action triggered")
            }
        } else {
            actButton.hide()
            actButton.setOnClickListener(null)
        }
    }

    private fun bindButtonActions() {
        backToMainBtn.setOnClickListener {
            finish()
        }

        // Listener para iniciar BandListActivity
        findViewById<ImageView>(R.id.imgArtistas).setOnClickListener {
            startActivity(
                Intent(this, BandListActivity::class.java),
                ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
            )
        }

        // Listener para iniciar CollectorListActivity
        findViewById<ImageView>(R.id.imgColeccionistas).setOnClickListener {
            startActivity(
                Intent(this, CollectorListActivity::class.java),
                ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
            )
        }

        // Listener para iniciar AlbumListActivity
        findViewById<ImageView>(R.id.imgAlbumes).setOnClickListener {
            startActivity(
                Intent(this, AlbumListActivity::class.java),
                ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
            )
        }
    }
}
