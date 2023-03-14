package com.example.turfnow.repository

import com.example.turfnow.database.entity.User

interface UserRepository{
    suspend fun addUser(user: User):Long
    suspend fun checkEmailExists(email: String): Boolean
    suspend fun checkPassword(email: String, password: String): User?
}