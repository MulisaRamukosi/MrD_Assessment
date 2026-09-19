package com.example.mrd_assessment.core.network.api

import com.example.mrd_assessment.model.Menu
import com.example.mrd_assessment.model.Restaurant
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantApiService {

    @GET("restaurants")
    suspend fun getRestaurants(): List<Restaurant>

    @GET("restaurants/{id}")
    suspend fun getRestaurantDetail(
        @Path("id") id: String
    ): Restaurant

    @GET("restaurants/{id}/menu")
    suspend fun getMenu(
        @Path("id") restaurantId: String
    ): Menu
}
