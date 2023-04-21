package com.example.turfnow.database.apiservice.faq

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface FaqApiServices {
    @GET("faq")
    suspend fun getFaqs(): Response<List<Faq>>

    object RetrofitBuilder{
        private const val BASE_URL = "http://192.168.43.14:9000/"
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.readTimeout(120, TimeUnit.SECONDS).build()

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