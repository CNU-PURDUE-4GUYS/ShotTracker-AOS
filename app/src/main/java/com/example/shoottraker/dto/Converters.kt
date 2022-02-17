package com.example.shoottraker.dto

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.shoottraker.model.Bullet
import com.google.gson.Gson
import java.io.ByteArrayOutputStream

class Converters {
    @TypeConverter
    fun toByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun from2DFloatToString(value: List<Bullet>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringTo2DFloat(value: String): List<Bullet> {
        return Gson().fromJson(value, Array<Bullet>::class.java).toList()
    }
}