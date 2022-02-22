package com.example.shoottraker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo val imageUri: String,
    @ColumnInfo val date: String,
    @ColumnInfo val totalBullet: String,
    @ColumnInfo val averageSize: String
)