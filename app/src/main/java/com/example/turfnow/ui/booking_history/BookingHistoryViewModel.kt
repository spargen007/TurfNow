package com.example.turfnow.ui.booking_history

import androidx.lifecycle.*
import com.example.turfnow.database.dao.BookingDao
import com.example.turfnow.dependency.Appcontainer
import com.example.turfnow.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingHistoryViewModel(private val bookingRepository: BookingRepository): ViewModel() {
    private val _bookingsList = MutableLiveData<List<BookingDao.BookingswithTurf>>()
    val bookingsList: LiveData<List<BookingDao.BookingswithTurf>> = _bookingsList

     fun getBookingHistoryList(userId: Long){
        viewModelScope.launch {
            _bookingsList.postValue(bookingRepository.getBookingHistoryList(userId))
        }
    }


}
class BookingHistoryViewModelFactory(private val appContainer: Appcontainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookingHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookingHistoryViewModel(appContainer.bookingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}