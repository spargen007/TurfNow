package com.example.turfnow.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.turfnow.database.entity.Turf
import kotlinx.coroutines.flow.Flow
@Dao
interface TurfDao {
        @Query("SELECT * FROM turf")
        fun getAllTurf(): Flow<List<Turf>>

        @Query("SELECT * FROM turf where name || location LIKE '%' || :search || '%' ")
        suspend fun getSearchResult(search:String): List<Turf>

        @Query("SELECT * FROM turf where  location LIKE '%' || :search || '%' ")
        suspend fun getlocationResult(search:String): List<Turf>

        @Query("SELECT * FROM turf WHERE id IN (:turfId)")
        suspend fun getTurfForId(turfId:List<String>): List<Turf>

        @Query("SELECT DISTINCT location FROM turf")
        fun getAllLocations():Flow<List<String>>
}