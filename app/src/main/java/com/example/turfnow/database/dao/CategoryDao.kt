package com.example.turfnow.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.turfnow.database.entity.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAllCategory(): Flow<List<Category>>
}