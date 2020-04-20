package br.com.rodrigolmti.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_password")
data class SavedPasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "strength") val strength: Int = 0,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "label") val label: String
)