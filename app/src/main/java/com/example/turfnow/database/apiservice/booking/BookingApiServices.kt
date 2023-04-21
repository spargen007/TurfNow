package com.example.turfnow.database.apiservice.booking

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface  BookingApiServices {
    @POST("addBooking")
    suspend fun addBooking(@Body bookingData:BookingData):Response<ResponseBody>

    @GET("bookings/{userId}")
    suspend fun getBookingsByUser(@Path("userId") userId: Long): Response<List<BookingData>>

        object RetrofitBuilder{
            private const val BASE_URL = "http://192.168.43.14:9000/"
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
            val apiService: BookingApiServices = getRetrofit().create(BookingApiServices::class.java)
        }
}