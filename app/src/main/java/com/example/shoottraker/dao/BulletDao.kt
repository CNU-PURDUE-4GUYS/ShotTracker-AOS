package com.example.shoottraker.dao

import androidx.room.*
import com.example.shoottraker.model.Bullet

@Dao
interface BulletDao {
    // Bring all bullet traces at the Room DB
    @Query("SELECT * FROM Bullet")
    fun getAllBullets(): List<Bullet>

    // Insert bullet trace in the Room DB
    @Insert
    fun insertPoint(bullet: Bullet)

    // Delete all bullet traces in the Room DB
    @Delete
    fun deleteBullets(bullets : List<Bullet>)
}