package br.com.rodrigolmti.database

import android.app.Application
import androidx.room.Room
import javax.inject.Inject

class AppPasswordDatabase @Inject constructor(private val app: Application) : PasswordDatabase {

    override fun database(): AppDatabase = Room.databaseBuilder(
        app.applicationContext,
        AppDatabase::class.java, "password-keeper-db",
    ).build()
}