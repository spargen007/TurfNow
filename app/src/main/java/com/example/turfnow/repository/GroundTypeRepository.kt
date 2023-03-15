package com.example.turfnow.repository

import kotlinx.coroutines.flow.Flow

interface GroundTypeRepository {
    suspend fun getGroundtypeofTurf(turfId:String): Flow<List<String>>
}