package com.example.turfnow.repository

import com.example.turfnow.database.dao.GroundTypeDao
import com.example.turfnow.database.entity.GroundType
import kotlinx.coroutines.flow.Flow

class GroundTypeRepositoryImpl(private val groundTypeDao: GroundTypeDao):GroundTypeRepository {
    override suspend fun getGroundtypeofTurf(turfId: String): Flow<List<String>> {
      return groundTypeDao.getGroundtypeofTurf(turfId)
    }

    override suspend fun getTurfIdsForCategory(categoryId: String): List<String> {
        return groundTypeDao.getTurfIdsForCategory(categoryId)
    }

    override suspend fun getGroundType(categoryId: String,turfId: String): GroundType {
        return groundTypeDao.getGroundType(categoryId,turfId)
    }

}