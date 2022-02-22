package com.example.shoottraker.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoottraker.model.History

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History")
    fun getHistory(): List<History>

    @Insert
    fun insertHistory(history: History)
}