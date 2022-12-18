package com.arceapps.newsapi.retrofit

import com.arceapps.newsapi.model.ArticlesModel
import com.arceapps.newsapi.utils.UtilConstants
import com.arceapps.newsapi.utils.UtilConstants.apiKey
import com.arceapps.newsapi.utils.UtilConstants.category
import com.arceapps.newsapi.utils.UtilConstants.country
import com.arceapps.newsapi.utils.UtilConstants.everything
import com.arceapps.newsapi.utils.UtilConstants.language
import com.arceapps.newsapi.utils.UtilConstants.numNews
import com.arceapps.newsapi.utils.UtilConstants.pageSize
import com.arceapps.newsapi.utils.UtilConstants.topHeadlines
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    companion object{
        operator fun invoke(): ApiInterface {
            return Retrofit.Builder()
                .baseUrl(UtilConstants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }


    @GET("$topHeadlines$country=ar&$pageSize=$numNews&$apiKey")
    fun getArticlesModel() : Call<ArticlesModel>

    @GET("$topHeadlines$country=ar&$pageSize=$numNews&$apiKey")
    suspend fun getTopHeadlines() : Response<ArticlesModel>

    @GET("$topHeadlines$category=technology&$country=ar&$pageSize=$numNews&$apiKey")
    suspend fun getArticles() : Response<ArticlesModel>

    @GET("$topHeadlines$category=technology&$country=ar&$pageSize=$numNews&$apiKey")
    suspend fun getTechnology() : Response<ArticlesModel>

    @GET("$topHeadlines$category=entertainment&$country=ar&$pageSize=$numNews&$apiKey")
    fun getEntertainmaent() : Call<ArticlesModel>

    @GET("$topHeadlines$category=entertainment&$country=ar&$pageSize=$numNews&$apiKey")
    suspend fun getEntertainment() : Response<ArticlesModel>

    @GET("$topHeadlines$category=business&$country=ar&$pageSize=$numNews&$apiKey")
    suspend fun getBusiness() : Response<ArticlesModel>

    @GET("$topHeadlines$category=general&$country=ar&$pageSize=$numNews&$apiKey")
    fun getGeneral() : Response<ArticlesModel>

    @GET("$topHeadlines$category=health&$country=ar&$pageSize=$numNews&$apiKey")
    suspend fun getHealth() : Response<ArticlesModel>

    @GET("$topHeadlines$category=science&$country=ar&$pageSize=$numNews&$apiKey")
    suspend fun getScience() : Response<ArticlesModel>

    @GET("$topHeadlines$category=sports&$country=ar&$pageSize=$numNews&$apiKey")
    suspend fun getSports() : Response<ArticlesModel>

    @GET("$everything$language=es&q=international&$pageSize=$numNews&$apiKey")
    suspend fun getInternational() : Response<ArticlesModel>

}