package com.example.turfnow.repository

import com.example.turfnow.database.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategory(): Flow<List<Category>>
}