package com.example.turfnow.database.apiservice.faq

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FaqApiServices {
    @GET("Faq.json")
    suspend fun getFaqs(): Response<List<Faq>>

    object RetrofitBuilder{
        private const val BASE_URL = "https://raw.githubusercontent.com/spargen007/Indian-Cities-JSON/master/"
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val apiService: FaqApiServices = getRetrofit().create(FaqApiServices::class.java)
    }
}