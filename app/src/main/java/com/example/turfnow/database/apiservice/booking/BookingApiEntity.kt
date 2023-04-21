package com.example.turfnow.database.apiservice.booking

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class BookingApiEntity(
    @SerializedName("id") @Expose(serialize = false) val id: Long? = null,
    val bookingDate:String,
    val userId:Long,
    val groundId: Long,
    val totalPrice:Double
)

data class BookingSlotsApiEntity(
    @SerializedName("bookingId") @Expose(serialize = false)
    val bookingId:Long?=null,
    @SerializedName("slot_time")
    val slot_time:String,
    @SerializedName("price")
    val price:Double
)

data class BookingData(
    val bookingApiEntity: BookingApiEntity,
    val bookingSlotsApiEntity:List<BookingSlotsApiEntity>
)