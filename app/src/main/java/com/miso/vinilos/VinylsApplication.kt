package com.miso.vinilos

import android.app.Application
import com.miso.vinilos.database.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}