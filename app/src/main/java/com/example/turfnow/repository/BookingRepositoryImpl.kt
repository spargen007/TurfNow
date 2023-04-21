package com.example.turfnow.repository

import com.example.turfnow.database.apiservice.booking.BookingApiServices
import com.example.turfnow.database.apiservice.booking.BookingData
import com.example.turfnow.database.dao.AvailabilityDao
import com.example.turfnow.database.dao.BookingDao
import com.example.turfnow.database.entity.BookingSlots
import com.example.turfnow.database.entity.Bookings
import okhttp3.ResponseBody
import retrofit2.Response

class BookingRepositoryImpl(private val bookingDao: BookingDao,private val apiServices: BookingApiServices):BookingRepository {
    override suspend fun insertBookingWithSlots(
        booking: Bookings,
        bookingSlots: List<AvailabilityDao.TimeSlot>
    ) {
        return bookingDao.insertBookingWithSlots(booking,bookingSlots)
    }

    override suspend fun getBookingHistoryList(userId: Long): List<BookingDao.BookingswithTurf> {
        return  bookingDao.getBookingHistoryList(userId)
    }
    override suspend fun addBookingToWeb(bookingData: BookingData): Response<ResponseBody> {
        return apiServices.addBooking(bookingData)
    }

    override suspend fun getBookingHistoryFromWeb(userId: Long): Response<List<BookingData>> {
        return apiServices.getBookingsByUser(userId)
    }

    override suspend fun getBookingHistoryListFromWeb(userId: Long):List<BookingDao.BookingswithTurf>{
        val bookingList = getBookingHistoryFromWeb(userId).body()
        println(bookingList)
        val bookingWithTurf:MutableList<BookingDao.BookingswithTurf> = mutableListOf()
        bookingList?.forEach {
            val groundType = bookingDao.getGroundTypeForId(it.bookingApiEntity.groundId)
            bookingWithTurf.add(
                BookingDao.BookingswithTurf(
                    BookingDao.BookingModel(Bookings(it.bookingApiEntity.id!!,it.bookingApiEntity.bookingDate,it.bookingApiEntity.userId,it.bookingApiEntity.groundId,it.bookingApiEntity.totalPrice),
                        it.bookingSlotsApiEntity.map {slot->
                            BookingSlots(slot.bookingId!!,slot.slot_time,slot.price)
                        },groundType),
                    bookingDao.getSingleTurfForId(groundType.turf_id.toLong()),
                    bookingDao.getSingleCategoryForId(groundType.category_id.toLong())
                )
            )
        }
        return bookingWithTurf
    }
}