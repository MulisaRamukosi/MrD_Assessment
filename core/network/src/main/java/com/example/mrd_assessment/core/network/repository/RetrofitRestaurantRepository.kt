package com.example.mrd_assessment.core.network.repository

import com.example.mrd_assessment.core.network.api.RestaurantApiService
import com.example.mrd_assessment.model.Menu
import com.example.mrd_assessment.model.Restaurant
import javax.inject.Inject

class RetrofitRestaurantRepository @Inject constructor(
    private val apiService: RestaurantApiService
) : RestaurantRepository {

    override suspend fun getRestaurants(): List<Restaurant> {
        return apiService.getRestaurants()
    }

    override suspend fun getRestaurantDetail(id: String): Restaurant {
        return apiService.getRestaurantDetail(id)
    }

    override suspend fun getMenu(restaurantId: String): Menu {
        return apiService.getMenu(restaurantId)
    }
}
