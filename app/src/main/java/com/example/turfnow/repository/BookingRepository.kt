package com.example.turfnow.repository

import com.example.turfnow.database.dao.AvailabilityDao
import com.example.turfnow.database.dao.BookingDao
import com.example.turfnow.database.entity.Bookings

interface BookingRepository {
    suspend fun insertBookingWithSlots(booking: Bookings, bookingSlots: List<AvailabilityDao.TimeSlot>)
    suspend fun getBookingHistoryList(userId: Long): List<BookingDao.BookingswithTurf>
}