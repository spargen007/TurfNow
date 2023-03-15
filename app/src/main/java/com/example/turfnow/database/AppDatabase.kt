package com.example.turfnow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.turfnow.database.dao.CategoryDao
import com.example.turfnow.database.dao.GroundTypeDao
import com.example.turfnow.database.dao.TurfDao
import com.example.turfnow.database.dao.UserDao
import com.example.turfnow.database.entity.Category
import com.example.turfnow.database.entity.GroundType
import com.example.turfnow.database.entity.Turf
import com.example.turfnow.database.entity.User

@Database(entities = [User::class,Turf::class,Category::class,GroundType::class], version = 7)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun turfDao():TurfDao
    abstract fun categoryDao():CategoryDao

    abstract fun groundTypeDao():GroundTypeDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "turfNow_Database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }

}