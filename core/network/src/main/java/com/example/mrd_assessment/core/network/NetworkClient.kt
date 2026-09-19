package com.example.mrd_assessment.core.network

import com.example.mrd_assessment.core.network.api.RestaurantApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkClient @Inject constructor(
    val apiService: RestaurantApiService
)
