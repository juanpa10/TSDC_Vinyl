package com.miso.vinilos.landing

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.miso.vinilos.R
import com.miso.vinilos.ui.listactivities.AlbumListActivity
import com.miso.vinilos.ui.listactivities.BandListActivity
import com.miso.vinilos.ui.listactivities.CollectorListActivity
import com.miso.vinilos.ui.listactivities.CreateAlbumActivity

class LandingActivity : AppCompatActivity() {

    private lateinit var actButton: FloatingActionButton
    private lateinit var backToMainBtn: ImageButton

    //Configuración expandir botón crear album, asociar track a album
    private var isFabExpanded = false
    // Referencias a los botones
    private lateinit var fabLeft: FloatingActionButton
    private lateinit var fabRight: FloatingActionButton
    private lateinit var textFabLeft: TextView
    private lateinit var textFabRight: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing)

        actButton = findViewById(R.id.act)
        backToMainBtn = findViewById(R.id.btnBackToMain)
        fabLeft = findViewById(R.id.fab_left)
        fabRight = findViewById(R.id.fab_right)
        textFabLeft = findViewById(R.id.text_fab_left)
        textFabRight = findViewById(R.id.text_fab_right)
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
            fabLeft.setOnClickListener {
                startActivity(
                    Intent(this, CreateAlbumActivity::class.java),
                    ActivityOptions.makeCustomAnimation(this, 0, 0).toBundle()
                )
            }
            actButton.setOnClickListener {
                val layoutParams = actButton.layoutParams as ConstraintLayout.LayoutParams
                // Acción para admin
                if (isFabExpanded) {
                    fabLeft.visibility = View.GONE
                    fabRight.visibility = View.GONE
                    textFabLeft.visibility = View.GONE
                    textFabRight.visibility = View.GONE
                    actButton.backgroundTintList = fabLeft.backgroundTintList
                    actButton.setImageResource(R.drawable.ic_add)
                    actButton.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.black))

                    // Restablecer las restricciones para alinear a la derecha
                    layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET // Remover restricción de inicio si existe
                    actButton.layoutParams = layoutParams

                    isFabExpanded = false
                } else {
                    fabLeft.visibility = View.VISIBLE
                    fabRight.visibility = View.VISIBLE
                    textFabLeft.visibility = View.VISIBLE
                    textFabRight.visibility = View.VISIBLE
                    actButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
                    actButton.setImageResource(R.drawable.ic_add)
                    actButton.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.white))

                    // Establecer las restricciones para centrar
                    layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    actButton.layoutParams = layoutParams

                    isFabExpanded = true
                }
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
