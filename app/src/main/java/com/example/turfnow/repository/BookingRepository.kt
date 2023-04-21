package com.example.turfnow.repository

import com.example.turfnow.database.apiservice.booking.BookingData
import com.example.turfnow.database.dao.AvailabilityDao
import com.example.turfnow.database.dao.BookingDao
import com.example.turfnow.database.entity.Bookings
import okhttp3.ResponseBody
import retrofit2.Response

interface BookingRepository {
    suspend fun insertBookingWithSlots(booking: Bookings, bookingSlots: List<AvailabilityDao.TimeSlot>)
    suspend fun getBookingHistoryList(userId: Long): List<BookingDao.BookingswithTurf>

    suspend fun addBookingToWeb(bookingData: BookingData): Response<ResponseBody>

    suspend fun getBookingHistoryFromWeb(userId: Long): Response<List<BookingData>>

    suspend fun getBookingHistoryListFromWeb(userId: Long):List<BookingDao.BookingswithTurf>
}