package com.example.turfnow.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.turfnow.database.entity.Turf
import kotlinx.coroutines.flow.Flow
@Dao
interface TurfDao {
        @Query("SELECT * FROM turf")
        fun getAllTurf(): Flow<List<Turf>>
}