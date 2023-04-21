package com.example.turfnow.ui.faq

import androidx.lifecycle.*
import com.example.turfnow.database.apiservice.faq.Faq
import com.example.turfnow.database.apiservice.faq.FaqRepository
import com.example.turfnow.dependency.Appcontainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Response

class FaqViewModel(private val faqRepository: FaqRepository): ViewModel() {
    private val _faqList = MutableLiveData<Response<List<Faq>>>()
    val faqList: LiveData<Response<List<Faq>>> = _faqList

    fun refresh(){
        viewModelScope.launch {
            delay(1000)
            getFaqs()
        }
    }

    fun getFaqs(){
    viewModelScope.launch {
        faqRepository.getfaqs().flowOn(Dispatchers.IO)
            .catch {e ->
            println(e)
            }
            .collect{
                _faqList.postValue(it)
            }
    }
    }

}
class FaqViewModelFactory(private val appContainer: Appcontainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FaqViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FaqViewModel(appContainer.faqRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}