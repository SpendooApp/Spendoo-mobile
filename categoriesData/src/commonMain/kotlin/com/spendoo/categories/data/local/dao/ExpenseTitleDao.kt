package com.spendoo.categories.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spendoo.categories.data.local.entity.ExpenseTitleEntity

@Dao
interface ExpenseTitleDao {
    @Query("SELECT title FROM expense_titles ORDER BY createdAt DESC")
    suspend fun getAllTitles(): List<String>

    @Query("SELECT title FROM expense_titles WHERE title LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    suspend fun searchTitles(query: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitles(titles: List<ExpenseTitleEntity>)
}
