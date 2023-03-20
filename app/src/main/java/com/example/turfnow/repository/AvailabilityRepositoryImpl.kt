package com.example.turfnow.repository

import com.example.turfnow.database.dao.AvailabilityDao

class AvailabilityRepositoryImpl(private val availabilityDao: AvailabilityDao):AvailabilityRepository {
    override suspend fun getallTimeSlots(groundTypeId: Long, date: String): List<AvailabilityDao.TimeSlot> {
        return availabilityDao.getallTimeSlots(groundTypeId,date)
    }

    override suspend fun getallPriceforTimeSlots(
        groundTypeId: Long,
        date: String,
        slotTime: List<String>
    ): List<Double> {
       return availabilityDao.getallPriceforTimeSlots(groundTypeId,date,slotTime)
    }

    override suspend fun checkAvailability(
        groundTypeId: Long,
        date: String,
        slotTime: List<String>
    ): List<AvailabilityDao.TimeSlot> {
        return  availabilityDao.checkAvailability(groundTypeId, date, slotTime)
    }

    override suspend fun markSlotAsBooked(groundTypeId: Long, date: String, slotTime: List<String>) {
        return availabilityDao.markSlotAsBooked(groundTypeId, date, slotTime)
    }
}