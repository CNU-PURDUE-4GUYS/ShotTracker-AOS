package com.example.shoottraker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoottraker.model.Reference

@Dao
interface ReferenceDao {
    // Save reference image in the Room DB
    @Insert
    fun insertReferenceImage(refUri: Reference)

    // Bring reference image using primary key at the Room DB
    @Query("SELECT refUri FROM Reference WHERE id = :id")
    fun getReferenceImage(id: Int): String

    // Bring all reference images in the Room DB
    @Query("SELECT * FROM Reference")
    fun getAllReferenceImage() : List<Reference>
}