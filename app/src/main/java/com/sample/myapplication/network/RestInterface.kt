package com.sample.myapplication.network

import com.sample.myapplication.model.network.CategoriesResponse
import com.sample.myapplication.model.network.RecepiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RestInterface {
    @Headers("Content-Type: application/json")
    @GET("/api/v2/recipes")
    suspend fun getRecepies(): Response<RecepiesResponse>

    @Headers("Content-Type: application/json")
    @GET("/api/v2/categories")
    suspend fun getCategories(): Response<CategoriesResponse>
}