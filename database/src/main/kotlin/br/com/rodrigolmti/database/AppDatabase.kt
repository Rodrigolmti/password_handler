package br.com.rodrigolmti.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rodrigolmti.database.dao.SavedPasswordDao
import br.com.rodrigolmti.database.entity.SavedPasswordEntity

@Database(entities = [SavedPasswordEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun savedPasswordDao(): SavedPasswordDao
}