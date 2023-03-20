package com.example.turfnow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.turfnow.database.dao.*
import com.example.turfnow.database.entity.*
import com.example.turfnow.util.Constants

@Database(entities = [User::class,Turf::class,Category::class,GroundType::class,Availability::class,
                     Bookings::class,BookingSlots::class], version = 10)
abstract class AppDatabase : RoomDatabase() {


    abstract fun userDao():UserDao
    abstract fun turfDao():TurfDao
    abstract fun categoryDao():CategoryDao

    abstract fun groundTypeDao():GroundTypeDao

    abstract fun availabilityDao():AvailabilityDao

    abstract fun bookingDao():BookingDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "turfNow_Database")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL(Constants.INITIAL_TURF_QUERY)
                            db.execSQL(Constants.INITIAL_CATEGORY_QUERY)
                            db.execSQL(Constants.INITIAL_GROUND_TYPE_QUERY)
                            db.execSQL(Constants.AVAILABILE_SLOTS)
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }

}