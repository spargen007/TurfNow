package com.example.turfnow.repository

import com.example.turfnow.database.dao.TurfDao
import com.example.turfnow.database.entity.Turf
import kotlinx.coroutines.flow.Flow

class TurfRepositoryImpl(private val turfDao: TurfDao):TurfRepository {
    override suspend fun getAllTurf(): Flow<List<Turf>> {
        return turfDao.getAllTurf()
    }

}