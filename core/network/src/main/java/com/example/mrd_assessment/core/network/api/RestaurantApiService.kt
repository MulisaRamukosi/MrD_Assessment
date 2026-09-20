package com.example.mrd_assessment.core.network.api

import com.example.mrd_assessment.model.Restaurant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RestaurantApiService {

    @POST("v1/restaurants")
    suspend fun getRestaurants(
        @Body request: GetRestaurantRequest
    ): Response<GetRestaurantResponse>


    @POST("v1/restaurants/{id}/favourite")
    suspend fun markRestaurantAsFavourite(@Path("id") restaurantId: String): Response<Boolean>
}

@Serializable
data class GetRestaurantRequest(
    @SerialName("page")
    val page: Int
)

@Serializable
data class GetRestaurantResponse(
    @SerialName("restaurants")
    val restaurants: List<Restaurant>,
    @SerialName("page")
    val page: Int,
    @SerialName("has_more_pages")
    val hasMorePages: Boolean,
)