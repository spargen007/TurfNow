package com.example.turfnow.repository

import com.example.turfnow.database.dao.CategoryDao
import com.example.turfnow.database.entity.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val categoryDao: CategoryDao):CategoryRepository {
    override suspend fun getAllCategory(): Flow<List<Category>> {
       return categoryDao.getAllCategory()
    }

    override suspend fun getCategoryForIds(categoryId: List<String>): Flow<List<Category>> {
       return categoryDao.getCategoryForIds(categoryId)
    }
}