package com.example.shoottraker.database

import android.content.Context
import androidx.room.*
import com.example.shoottraker.dao.BulletDao
import com.example.shoottraker.dto.Converters
import com.example.shoottraker.model.Bullet

@Database(entities = [Bullet::class], version = 1)
@TypeConverters(Converters::class)
abstract class BulletDatabase : RoomDatabase() {
    abstract fun BulletDao(): BulletDao

    companion object {
        private var instance: BulletDatabase? = null

        @Synchronized
        fun getInstance(context: Context): BulletDatabase? {
            if (instance == null) {
                synchronized(BulletDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BulletDatabase::class.java,
                        "bullet-database"
                    ).build()
                }
            }
            return instance
        }
    }

}