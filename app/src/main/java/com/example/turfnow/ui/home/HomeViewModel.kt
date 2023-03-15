package com.example.turfnow.ui.home

import androidx.lifecycle.*
import com.example.turfnow.database.entity.Category
import com.example.turfnow.database.entity.Turf
import com.example.turfnow.repository.CategoryRepository
import com.example.turfnow.repository.TurfRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val turfRepository: TurfRepository,private val categoryRepository: CategoryRepository): ViewModel() {
    private val _turfList = MutableLiveData<List<Turf>>()
    val turfList: LiveData<List<Turf>> = _turfList
    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>> = _categoryList
    init {
        getAllTurfAndCategoryList()
    }
    private fun getAllTurfAndCategoryList(){
        viewModelScope.launch(Dispatchers.IO){
            turfRepository.getAllTurf().collect{
                _turfList.postValue(it)
            }
        }
        viewModelScope.launch(Dispatchers.IO){
            categoryRepository.getAllCategory().collect {
                _categoryList.postValue(it)
            }
        }
    }
}
class HomeViewModelFactory(private val turfRepository: TurfRepository,private val categoryRepository: CategoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(turfRepository,categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
