package com.example.turfnow.database.apiservice

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FaqRepository {
    fun getfaqs(): Flow<Response<List<Faq>>>
}