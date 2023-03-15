package com.example.turfnow.database.dao

import androidx.room.*
import com.example.turfnow.database.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
     fun getAll(): List<User>
    @Query("SELECT * FROM user WHERE email_id = :email")
     fun getUserByEmail(email:String):User?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(user: User):Long
}