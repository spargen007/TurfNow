package com.example.turfnow.database.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AvailabilityDao {

//    @Query("SELECT slot_time FROM Availability WHERE ground_type_id=:groundTypeId AND date=:date")
//    fun getallTimeSlots(groundTypeId: Long,date:String): List<String>

    @Query("SELECT slot_time, is_booked, price FROM Availability WHERE ground_type_id=:groundTypeId AND date=:date")
    fun getallTimeSlots(groundTypeId: Long, date: String): List<TimeSlot>

    @Query("SELECT price FROM Availability WHERE ground_type_id=:groundTypeId AND date=:date AND slot_time IN (:slotTime)")
    fun getallPriceforTimeSlots(groundTypeId: Long, date: String,slotTime:List<String>): List<Double>

    @Query("SELECT slot_time, is_booked, price FROM Availability WHERE ground_type_id=:groundTypeId AND date=:date AND slot_time IN (:slotTime)")
    fun checkAvailability(groundTypeId: Long, date: String,slotTime:List<String>): List<TimeSlot>

    @Query("UPDATE availability SET is_booked = 1 WHERE ground_type_id = :groundTypeId AND date = :date AND slot_time IN (:slotTime)")
    suspend fun markSlotAsBooked(groundTypeId: Long, date: String, slotTime: List<String>)


    data class TimeSlot(
        @ColumnInfo(name = "slot_time")
        val slot_time: String,
        @ColumnInfo(name = "is_booked")
        val booked: Boolean,
        @ColumnInfo(name = "price")
        val price:Double
    )


}