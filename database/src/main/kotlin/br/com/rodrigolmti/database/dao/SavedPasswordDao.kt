package br.com.rodrigolmti.database.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.rodrigolmti.database.entity.SavedPasswordEntity

@Dao
interface SavedPasswordDao {

    @Query("SELECT * FROM saved_password")
    fun getAll(): List<SavedPasswordEntity>
}