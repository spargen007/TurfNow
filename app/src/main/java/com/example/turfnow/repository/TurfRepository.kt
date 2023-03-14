package com.example.turfnow.repository

import com.example.turfnow.database.entity.Turf
import kotlinx.coroutines.flow.Flow

interface TurfRepository {
    suspend fun getAllTurf(): Flow<List<Turf>>
}