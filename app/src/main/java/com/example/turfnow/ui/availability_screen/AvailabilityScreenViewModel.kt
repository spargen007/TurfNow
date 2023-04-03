package com.example.turfnow.ui.availability_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.turfnow.database.dao.AvailabilityDao
import com.example.turfnow.database.entity.Bookings
import com.example.turfnow.dependency.Appcontainer
import com.example.turfnow.repository.AvailabilityRepository
import com.example.turfnow.repository.BookingRepository

class AvailabilityScreenViewModel(private val availabilityRepository: AvailabilityRepository,private val bookingRepository: BookingRepository): ViewModel(){
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getallAvailableTimeSlot(groundTypeId: Long, date:String): List<AvailabilityDao.TimeSlot> {
            return availabilityRepository.getallTimeSlots(groundTypeId, date)
    }
    suspend fun getallPrice(groundTypeId: Long,date: String,slotTime:List<String>):List<Double>{
        return availabilityRepository.getallPriceforTimeSlots(groundTypeId, date, slotTime)
    }
    suspend fun checkAvailability(groundTypeId: Long,date: String,slotTime: List<String>):List<AvailabilityDao.TimeSlot>{
        return availabilityRepository.checkAvailability(groundTypeId, date, slotTime)
    }
    suspend fun markSlotAsBooked(groundTypeId: Long, date: String, slotTime: List<String>){
        return availabilityRepository.markSlotAsBooked(groundTypeId, date, slotTime)
    }
    suspend fun insertBookingWithSlots(booking: Bookings, bookingSlots: List<AvailabilityDao.TimeSlot>) {
        return bookingRepository.insertBookingWithSlots(booking, bookingSlots)
    }
}
class AvailabilityScreenViewModelFactory(private val appContainer: Appcontainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvailabilityScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AvailabilityScreenViewModel(appContainer.availabilityRepository,appContainer.bookingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}