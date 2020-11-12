package cookmap.cookandroid.hw.tdcalendar.controller

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object SearchRefrofit{
    fun getService() = retrofit.create(ApiService::class.java)

    private val retrofit = Retrofit.Builder().baseUrl("https://beewoo01.tk/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}