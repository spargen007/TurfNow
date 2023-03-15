package com.example.turfnow.ui.login

import androidx.lifecycle.*
import com.example.turfnow.repository.UserRepository
import com.example.turfnow.result.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository):ViewModel(){
    private val _loginUsersDataStatus = MutableLiveData<Response?>()
    val loginUsersDataStatus: LiveData<Response?> = _loginUsersDataStatus
    fun login(id:String,password:String){
        _loginUsersDataStatus.value=null
        viewModelScope.launch(Dispatchers.IO){
            _loginUsersDataStatus.postValue(Response.Loading)
            delay(2000)
            if(userRepository.checkEmailExists(id)) {
                try {
                    val user = userRepository.checkPassword(id, password)
                    if(user != null) {
                            _loginUsersDataStatus.postValue(Response.Success(user))
                        }
                    else{
                        _loginUsersDataStatus.postValue(Response.Error("Incorrect Password"))
                        }
                } catch (exception: Exception) {
                    _loginUsersDataStatus.postValue(Response.Error(exception.message))
                }
            }
            else{
                _loginUsersDataStatus.postValue(Response.Error("Email-Id doesn't exits!!! Pls Try again.."))
            }
        }
    }
}
class LoginViewModelFactory(private var userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
