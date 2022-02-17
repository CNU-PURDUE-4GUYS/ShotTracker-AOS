package com.example.shoottraker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bullet(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo val setId: Int,
    @ColumnInfo val x : Float,
    @ColumnInfo val y : Float,
)
