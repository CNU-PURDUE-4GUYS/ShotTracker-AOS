package com.example.shoottraker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey val id: String,
    @ColumnInfo val totalSet: String,
    @ColumnInfo val totalBullet: String,
    @ColumnInfo val averageSize: String
)