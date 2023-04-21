package com.example.turfnow.dependency

import android.content.Context
import com.example.turfnow.database.AppDatabase
import com.example.turfnow.database.apiservice.booking.BookingApiServices
import com.example.turfnow.database.apiservice.faq.FaqApiServices
import com.example.turfnow.database.apiservice.faq.FaqRepositoryImpl
import com.example.turfnow.repository.*

class Appcontainer(private val context:Context){
    private val dataservice = AppDatabase.getDatabase(context)
    private val bookingApiServices = BookingApiServices.RetrofitBuilder.apiService
    private val apiServices = FaqApiServices.RetrofitBuilder.apiService
    val userRepository by lazy {
        UserRepositoryImpl(dataservice.userDao())
    }
    val categoryRepository by lazy {
        CategoryRepositoryImpl(dataservice.categoryDao())
    }
    val groundTypeRepository by lazy {
        GroundTypeRepositoryImpl(dataservice.groundTypeDao())
    }
    val turfRepository by lazy {
        TurfRepositoryImpl(dataservice.turfDao())
    }
    val availabilityRepository by lazy {
        AvailabilityRepositoryImpl(dataservice.availabilityDao())
    }
    val bookingRepository by lazy{
        BookingRepositoryImpl(dataservice.bookingDao(), bookingApiServices)
    }
    val faqRepository by lazy {
        FaqRepositoryImpl(apiServices)
    }

}