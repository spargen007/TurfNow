package com.example.turfnow.database.dao

import androidx.room.*
import com.example.turfnow.database.entity.*

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Bookings): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookingSlots(bookingSlots: List<BookingSlots>)

    @Transaction
    suspend fun insertBookingWithSlots(booking: Bookings, bookingSlots: List<AvailabilityDao.TimeSlot>) {
        val bookingId = insertBooking(booking)
        val bookingList = mutableListOf<BookingSlots>()
        for (bookingSlot in bookingSlots) {
            bookingList.add(BookingSlots(bookingId,bookingSlot.slot_time,bookingSlot.price))
        }
        insertBookingSlots(bookingList)
    }

    @Transaction
    @Query("SELECT * FROM booking WHERE user_id = :userId")
    suspend fun getBookingList(userId: Long): List<BookingModel>

    data class BookingModel(
        @Embedded val booking: Bookings,
        @Relation(
            parentColumn = "id",
            entityColumn = "booking_id",
            entity = BookingSlots::class
        )
        val bookingSlots: List<BookingSlots>,
        @Relation(
            parentColumn = "ground_id",
            entityColumn = "id",
            entity = GroundType::class
        )
        val groundType: GroundType
    )
    @Query("SELECT * FROM turf WHERE id = :turfId")
    suspend fun getSingleTurfForId(turfId:Long):Turf

    @Query("SELECT * FROM category WHERE id = :categoryId")
    suspend fun getSingleCategoryForId(categoryId:Long):Category

    @Transaction
    suspend fun getBookingHistoryList(userId: Long):List<BookingswithTurf>{
        val bookingList = getBookingList(userId)
        val bookingWithTurf:MutableList<BookingswithTurf> = mutableListOf()
        bookingList.forEach {
           bookingWithTurf.add(BookingswithTurf(it,getSingleTurfForId(it.groundType.turf_id.toLong()),getSingleCategoryForId(it.groundType.category_id.toLong())))
        }
        return bookingWithTurf
    }
    data class BookingswithTurf(
        val bookingModel: BookingModel,
        val turf: Turf,
        val category: Category
    )

}
