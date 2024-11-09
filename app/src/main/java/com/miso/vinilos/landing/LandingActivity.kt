package com.miso.vinilos.landing

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.miso.vinilos.R
import com.miso.vinilos.main.MainActivity
import com.miso.vinilos.ui.listactivities.AlbumListActivity
import com.miso.vinilos.ui.listactivities.BandListActivity

class LandingActivity : AppCompatActivity() {

    private lateinit var actButton: FloatingActionButton
    private lateinit var backToMainBtn: ImageButton

    //Configuración expandir botón crear album, asociar track a album
    private var isFabExpanded = false
    // Referencias a los botones
    private lateinit var fabLeft: FloatingActionButton
    private lateinit var fabRight: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing)

        actButton = findViewById(R.id.act)
        backToMainBtn = findViewById(R.id.btnBackToMain)
        fabLeft = findViewById(R.id.fab_left)
        fabRight = findViewById(R.id.fab_right)

        validateAdmin()
        bindButtonActions()
    }

    private fun validateAdmin() {
        val isAdmin = intent.getBooleanExtra("isAdmin", false)
        if (isAdmin) {
            actButton.show()
            actButton.setOnClickListener {
                val layoutParams = actButton.layoutParams as ConstraintLayout.LayoutParams
                // Acción para admin
                if (isFabExpanded) {
                    fabLeft.visibility = View.GONE
                    fabRight.visibility = View.GONE
                    actButton.setImageResource(R.drawable.ic_add)
                    actButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.backgroundPrimary))

                    // Restablecer las restricciones para alinear a la derecha
                    layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET // Remover restricción de inicio si existe
                    actButton.layoutParams = layoutParams

                    isFabExpanded = false
                } else {
                    fabLeft.visibility = View.VISIBLE
                    fabRight.visibility = View.VISIBLE
                    actButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
                    actButton.setImageResource(R.drawable.ic_add)

                    // Establecer las restricciones para centrar
                    layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    actButton.layoutParams = layoutParams
                    
                    isFabExpanded = true
                }
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
            val intent = Intent(this, AlbumListActivity::class.java)
            startActivity(intent)
        }
    }
}