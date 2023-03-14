package com.example.turfnow.database.dao

import androidx.room.*
import com.example.turfnow.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>
    @Query("SELECT * FROM user WHERE email_id = :email")
    suspend fun getUserByEmail(email:String):User?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User):Long
}