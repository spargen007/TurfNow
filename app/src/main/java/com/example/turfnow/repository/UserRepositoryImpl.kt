package com.example.turfnow.repository

import com.example.turfnow.database.dao.UserDao
import com.example.turfnow.database.entity.User

class UserRepositoryImpl(private var userDao: UserDao):UserRepository {
    override suspend fun addUser(user: User): Long {
        return userDao.insert(user)
    }

    override suspend fun checkEmailExists(email: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return user != null
    }

    override suspend fun checkPassword(email: String, password: String): User?{
        val user = userDao.getUserByEmail(email)
        if (user != null) {
            if (user.password == password) {
                return user
            }
            return null
        }
        return null
    }

    override fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}