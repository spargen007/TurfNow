package com.example.turfnow.ui.singleturf

import androidx.lifecycle.*
import com.example.turfnow.database.entity.Category
import com.example.turfnow.database.entity.GroundType
import com.example.turfnow.dependency.Appcontainer
import com.example.turfnow.repository.CategoryRepository
import com.example.turfnow.repository.GroundTypeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleTurfViewModel(private val groundTypeRepository: GroundTypeRepository,private val categoryRepository: CategoryRepository): ViewModel() {
        private val _categoryList = MutableLiveData<List<Category>>()
        val categoryList: LiveData<List<Category>> = _categoryList

    fun getGroundtypeofTurf(turfId:String){
        viewModelScope.launch(Dispatchers.IO){
          groundTypeRepository.getGroundtypeofTurf(turfId).collect{
              categoryRepository.getCategoryForIds(it).collect {category->
                  _categoryList.postValue(category)
              }
            }
        }
    }
    suspend fun getGroundType(turfId: String, categoryId: String):GroundType{
        return groundTypeRepository.getGroundType(categoryId,turfId)
    }
}
class SingleTurfViewModelFactory(private val appContainer: Appcontainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleTurfViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SingleTurfViewModel(appContainer.groundTypeRepository,appContainer.categoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}