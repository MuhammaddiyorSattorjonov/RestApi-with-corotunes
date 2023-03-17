package com.example.restapiwithcorotunes.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://hvax.pythonanywhere.com/"

    fun getRetRofit(): Retrofit{
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getApiService():ApiService{
        return getRetRofit().create(ApiService::class.java)
    }
}