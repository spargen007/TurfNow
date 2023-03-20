package com.example.turfnow.repository

import com.example.turfnow.database.entity.Turf
import kotlinx.coroutines.flow.Flow

interface TurfRepository {
    suspend fun getAllTurf(): Flow<List<Turf>>
    suspend fun getTurfForId(turfId:List<String>): List<Turf>

    suspend fun getSearchResult(search:String): List<Turf>

    suspend fun getAllLocations():Flow<List<String>>

    suspend fun getlocationResult(search:String): List<Turf>
}