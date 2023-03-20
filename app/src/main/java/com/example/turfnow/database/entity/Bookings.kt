package com.example.turfnow.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "booking", foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE), ForeignKey(entity = GroundType::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("ground_id"),
        onDelete = ForeignKey.CASCADE),])
data class Bookings(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long,
    @ColumnInfo(name = "booking_date")
    val booked_date:String,
    @ColumnInfo(name = "user_id")
    val user_id:Long,
    @ColumnInfo(name = "ground_id")
    val groundType_id: Long,
    @ColumnInfo(name = "total_price")
    val total_price:Double
)
@Entity(tableName = "booking_slots", primaryKeys = ["booking_id","slot_time"], foreignKeys = [ForeignKey(entity = Bookings::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("booking_id"),
    onDelete = ForeignKey.CASCADE)])

data class BookingSlots(
    @ColumnInfo(name = "booking_id")
    val bookingId: Long,
    @ColumnInfo(name = "slot_time")
    val slotTime: String,
    @ColumnInfo(name = "price")
    val price:Double
)