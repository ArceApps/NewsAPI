package com.arceapps.newsapi.retrofit

import com.arceapps.newsapi.utils.UtilConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    lateinit var retrofit: Retrofit

    fun getClient() : Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl(UtilConstants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit
    }
}