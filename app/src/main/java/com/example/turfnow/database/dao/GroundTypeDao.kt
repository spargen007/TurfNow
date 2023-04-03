package com.example.turfnow.database.dao

import androidx.room.*
import com.example.turfnow.database.entity.GroundType
import kotlinx.coroutines.flow.Flow

@Dao
interface GroundTypeDao {
    @Query("SELECT category_id FROM ground_type WHERE turf_id =:turfId")
     fun getGroundtypeofTurf(turfId:String): Flow<List<String>>

    @Query("SELECT turf_id FROM ground_type WHERE category_id =:categoryId")
    fun getTurfIdsForCategory(categoryId:String): List<String>
//
    @Query("SELECT ground_type.* FROM ground_type JOIN category ON category.id = ground_type.category_id WHERE category.name = :categoryName AND ground_type.turf_id = :turfId")
    fun getGroundType(categoryName:String,turfId: String): GroundType

}