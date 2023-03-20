package com.example.turfnow.repository

import com.example.turfnow.database.dao.AvailabilityDao
import com.example.turfnow.database.dao.BookingDao
import com.example.turfnow.database.entity.Bookings

class BookingRepositoryImpl(private val bookingDao: BookingDao):BookingRepository {
    override suspend fun insertBookingWithSlots(
        booking: Bookings,
        bookingSlots: List<AvailabilityDao.TimeSlot>
    ) {
        return bookingDao.insertBookingWithSlots(booking,bookingSlots)
    }

    override suspend fun getBookingHistoryList(userId: Long): List<BookingDao.BookingswithTurf> {
        return  bookingDao.getBookingHistoryList(userId)
    }

}