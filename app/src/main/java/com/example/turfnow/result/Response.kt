package com.example.turfnow.result

sealed class Response{

    object Loading:Response()

    data class Success(val data: Any) : Response()

    data class Error(val message: String?) : Response()

}