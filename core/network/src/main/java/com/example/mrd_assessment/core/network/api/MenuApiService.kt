package com.example.mrd_assessment.core.network.api

import com.example.mrd_assessment.model.Menu
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path

interface MenuApiService {
    @GET("v1/restaurants/{id}/menu")
    suspend fun getMenu(
        @Path("id") restaurantId: String
    ): Response<Menu>
}