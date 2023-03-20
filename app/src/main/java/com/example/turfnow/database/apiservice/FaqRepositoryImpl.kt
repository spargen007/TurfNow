package com.example.turfnow.database.apiservice

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FaqRepositoryImpl(private val apiServices: FaqApiServices):FaqRepository{
    override fun getfaqs(): Flow<Response<List<Faq>>> = flow{
        emit(apiServices.getFaqs())
    }

}