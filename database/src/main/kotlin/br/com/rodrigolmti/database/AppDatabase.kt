package br.com.rodrigolmti.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.rodrigolmti.database.dao.BackupPinDao
import br.com.rodrigolmti.database.dao.SavedPasswordDao
import br.com.rodrigolmti.database.entity.BackupPinEntity
import br.com.rodrigolmti.database.entity.SavedPasswordEntity

@Database(
    entities = [SavedPasswordEntity::class, BackupPinEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun savedPasswordDao(): SavedPasswordDao

    abstract fun backupPinDao(): BackupPinDao
}