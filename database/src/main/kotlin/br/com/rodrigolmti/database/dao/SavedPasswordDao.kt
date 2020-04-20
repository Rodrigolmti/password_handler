package br.com.rodrigolmti.database.dao

import androidx.room.*
import br.com.rodrigolmti.database.entity.SavedPasswordEntity

@Dao
interface SavedPasswordDao {

    @Query("SELECT * FROM saved_password")
    fun getAll(): List<SavedPasswordEntity>

    @Insert
    fun insert(entity: SavedPasswordEntity)

    @Update
    fun update(entity: SavedPasswordEntity)

    @Delete
    fun delete(entity: SavedPasswordEntity)
}