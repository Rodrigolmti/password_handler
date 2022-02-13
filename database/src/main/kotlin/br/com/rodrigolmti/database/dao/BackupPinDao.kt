package br.com.rodrigolmti.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.rodrigolmti.database.entity.BackupPinEntity

@Dao
interface BackupPinDao {

    @Query("SELECT * FROM backup_pin_entity")
    fun getPin(): List<BackupPinEntity>

    @Insert
    fun insert(entity: BackupPinEntity)

    @Query("DELETE FROM backup_pin_entity")
    fun delete()
}