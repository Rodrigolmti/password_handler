package br.com.rodrigolmti.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "backup_pin_entity")
data class BackupPinEntity(
    @PrimaryKey
    @ColumnInfo(name = "pin")
    val pin: String,
)