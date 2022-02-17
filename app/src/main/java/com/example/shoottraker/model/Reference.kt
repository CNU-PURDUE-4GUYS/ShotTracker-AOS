package com.example.shoottraker.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reference(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo val refUri: String,
)
