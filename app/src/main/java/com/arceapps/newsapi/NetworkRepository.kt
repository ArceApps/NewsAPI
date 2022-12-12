package com.arceapps.newsapi

import com.arceapps.newsapi.retrofit.ApiInterface
import com.arceapps.newsapi.retrofit.SafeApiRequest

class NetworkRepository(
    val apiInterface: ApiInterface
): SafeApiRequest() {

    suspend fun getBusiness() = apiRequest { apiInterface.getBusiness() }

    suspend fun getEntertainment() = apiRequest { apiInterface.getEntertainment() }

    suspend fun getSports() = apiRequest { apiInterface.getSports() }

    suspend fun getScience() = apiRequest { apiInterface.getScience() }

    suspend fun getTechnology() = apiRequest { apiInterface.getTechnology() }

    suspend fun getMedical() = apiRequest { apiInterface.getHealth() }

    suspend fun getInternational() = apiRequest { apiInterface.getInternational() }

    suspend fun getTopHeadlines() = apiRequest { apiInterface.getTopHeadlines() }

    suspend fun getArticles() = apiRequest { apiInterface.getArticles() }

}