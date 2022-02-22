package com.example.shoottraker.database

import android.content.Context
import androidx.room.*
import com.example.shoottraker.dao.HistoryDao
import com.example.shoottraker.dto.Converters
import com.example.shoottraker.model.History

@Database(entities = [History::class], version = 1)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun HistoryDao(): HistoryDao

    companion object {
        private var instance: HistoryDatabase? = null

        @Synchronized
        fun getInstance(context: Context?): HistoryDatabase? {
            if (instance == null) {
                synchronized(HistoryDatabase::class) {
                    instance = Room.databaseBuilder(
                        context!!.applicationContext,
                        HistoryDatabase::class.java,
                        "history-database"
                    ).build()
                }
            }
            return instance
        }
    }

}