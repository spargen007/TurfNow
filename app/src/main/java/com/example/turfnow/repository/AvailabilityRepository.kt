package com.example.turfnow.repository

import com.example.turfnow.database.dao.AvailabilityDao

interface AvailabilityRepository {

   suspend fun getallTimeSlots(groundTypeId: Long, date: String): List<AvailabilityDao.TimeSlot>
   suspend fun getallPriceforTimeSlots(groundTypeId: Long, date: String,slotTime:List<String>): List<Double>
   suspend  fun checkAvailability(groundTypeId: Long, date: String,slotTime:List<String>): List<AvailabilityDao.TimeSlot>
   suspend fun markSlotAsBooked(groundTypeId:Long, date: String, slotTime: List<String>)
}