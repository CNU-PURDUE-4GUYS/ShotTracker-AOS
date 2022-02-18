package com.example.shoottraker.database

import android.content.Context
import androidx.room.*
import com.example.shoottraker.dao.ReferenceDao
import com.example.shoottraker.dto.Converters
import com.example.shoottraker.model.Reference

@Database(entities = [Reference::class], version = 1)
@TypeConverters(Converters::class)
abstract class ReferenceDatabase : RoomDatabase() {
    abstract fun ReferenceDao(): ReferenceDao

    companion object {
        private var instance: ReferenceDatabase? = null

        @Synchronized
        fun getInstance(context: Context?): ReferenceDatabase? {
            if (instance == null) {
                synchronized(ReferenceDatabase::class) {
                    instance = Room.databaseBuilder(
                        context!!.applicationContext,
                        ReferenceDatabase::class.java,
                        "reference-database"
                    ).build()
                }
            }
            return instance
        }
    }
}