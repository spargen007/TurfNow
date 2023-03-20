package com.example.turfnow.ui.login

import androidx.lifecycle.*
import com.example.turfnow.dependency.Appcontainer
import com.example.turfnow.repository.UserRepository
import com.example.turfnow.result.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(private val userRepository: UserRepository):ViewModel() {
    private lateinit var _loginUsersDataStatus: Response
    suspend fun login(id: String, password: String):Response{
        return withContext(Dispatchers.IO){
            try {
                _loginUsersDataStatus = if (userRepository.checkEmailExists(id)) {
                    val user = userRepository.checkPassword(id, password)
                    if (user != null) {
                        Response.Success(user)
                    } else {
                        Response.Error("Incorrect Password")
                    }
                } else {
                    Response.Error("Email-Id doesn't exits!!! Pls Try again..")
                }
            } catch (exception: Exception) {
                Response.Error(exception.message)
            }
            _loginUsersDataStatus
        }
    }
}
class LoginViewModelFactory(private var appContainer: Appcontainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(appContainer.userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
