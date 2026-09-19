package com.example.mrd_assessment.core.network.repository

import com.example.mrd_assessment.model.Menu
import com.example.mrd_assessment.model.Restaurant

interface RestaurantRepository {
    suspend fun getRestaurants(): List<Restaurant>
    suspend fun getRestaurantDetail(id: String): Restaurant
    suspend fun getMenu(restaurantId: String): Menu
}
