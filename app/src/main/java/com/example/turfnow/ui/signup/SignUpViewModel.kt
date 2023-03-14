package com.example.turfnow.ui.signup

import androidx.lifecycle.*
import com.example.turfnow.database.entity.User
import com.example.turfnow.repository.UserRepository
import com.example.turfnow.result.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpViewModel(private var userRepository: UserRepository):ViewModel(){
  private val _insertUsersDataStatus = MutableLiveData<Response?>()
  val insertUsersDataStatus: LiveData<Response?> = _insertUsersDataStatus
  fun register(user: User){
    _insertUsersDataStatus.postValue(null)
    viewModelScope.launch {
      _insertUsersDataStatus.postValue(Response.Loading)
      delay(2000)
      if(!userRepository.checkEmailExists(user.email_id)) {
        try {
          val data = userRepository.addUser(user)
          _insertUsersDataStatus.postValue(Response.Success(data))
        } catch (exception: Exception) {
          _insertUsersDataStatus.postValue(Response.Error(exception.message))
        }
      }
      else{
        _insertUsersDataStatus.postValue(Response.Error("Email-Id already exits!!! Pls Try again.."))
      }
    }
  }
}
class SignUpViewModelFactory(private var userRepository: UserRepository) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return SignUpViewModel(userRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}