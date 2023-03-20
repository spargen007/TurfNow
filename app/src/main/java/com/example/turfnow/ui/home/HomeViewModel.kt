package com.example.turfnow.ui.home

import androidx.lifecycle.*
import com.example.turfnow.database.entity.Category
import com.example.turfnow.database.entity.Turf
import com.example.turfnow.database.entity.User
import com.example.turfnow.dependency.Appcontainer
import com.example.turfnow.repository.CategoryRepository
import com.example.turfnow.repository.GroundTypeRepository
import com.example.turfnow.repository.TurfRepository
import com.example.turfnow.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val turfRepository: TurfRepository,private val categoryRepository: CategoryRepository,
private val groundTypeRepository: GroundTypeRepository,
private val userRepository: UserRepository): ViewModel() {
    var selectedLocation = MutableLiveData<String>()
    private val _turfList = MutableLiveData<List<Turf>>()
    val turfList: LiveData<List<Turf>> = _turfList
    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>> = _categoryList
    private val _locationList = MutableLiveData<List<String>>()
    val locationList: LiveData<List<String>> = _locationList
    init {
        getAllTurfAndCategoryAndLocationList()
    }
     fun getAllTurfAndCategoryAndLocationList(){
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
        viewModelScope.launch(Dispatchers.IO){
            turfRepository.getAllLocations().collect{
                _locationList.postValue(it)
            }
        }

    }
    suspend fun getSearchResult(search:String):List<Turf>{
        return withContext(Dispatchers.IO){
            turfRepository.getSearchResult(search)
        }
    }
    suspend fun getLocationResult(search:String):List<Turf>{
        return withContext(Dispatchers.IO){
            turfRepository.getlocationResult(search)
        }
    }
    suspend fun getTurfForCategory(category: Category):List<Turf>{
        return withContext(Dispatchers.IO){
            val categoryIdList= groundTypeRepository.getTurfIdsForCategory(category.id.toString())
            val turfList1 = turfRepository.getTurfForId(categoryIdList)
            turfList1
        }

    }
    suspend fun getUser(emailId:String):User?{
           return withContext(Dispatchers.IO) {
               userRepository.getUserByEmail(emailId)
            }
    }

    fun setSelectedLocation(location: String) {
        selectedLocation.value = location
    }
    fun updateTurfList(list:List<Turf>){
        _turfList.postValue(list)
        println(list.toString())
    }

}
class HomeViewModelFactory(private val appContainer: Appcontainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(appContainer.turfRepository,appContainer.categoryRepository,appContainer.groundTypeRepository,appContainer.userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
