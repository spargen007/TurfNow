package com.example.turfnow.repository

import com.example.turfnow.database.entity.GroundType
import kotlinx.coroutines.flow.Flow

interface GroundTypeRepository {
    suspend fun getGroundtypeofTurf(turfId:String): Flow<List<String>>
    suspend fun getTurfIdsForCategory(categoryId:String): List<String>

    suspend  fun getGroundType(categoryId:String,turfId: String): GroundType


}