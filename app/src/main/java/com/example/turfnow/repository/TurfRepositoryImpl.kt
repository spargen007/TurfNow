package com.example.turfnow.repository

import com.example.turfnow.database.dao.TurfDao
import com.example.turfnow.database.entity.Turf
import kotlinx.coroutines.flow.Flow

class TurfRepositoryImpl(private val turfDao: TurfDao):TurfRepository {
    override suspend fun getAllTurf(): Flow<List<Turf>> {
        return turfDao.getAllTurf()
    }

    override suspend fun getTurfForId(turfId: List<String>): List<Turf>{
        return turfDao.getTurfForId(turfId)
    }
    override suspend fun getSearchResult(search:String): List<Turf>{
        return turfDao.getSearchResult(search)
    }

    override suspend fun getAllLocations():Flow<List<String>>{
        return turfDao.getAllLocations()
    }

    override suspend fun getlocationResult(search:String): List<Turf>{
        return turfDao.getlocationResult(search)
    }

}