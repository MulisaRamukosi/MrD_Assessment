package com.example.mrd_assessment.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screen

@Serializable
object HomeRoute : Screen

@Serializable
data class RestaurantDetailRoute(
    val restaurantId: String
) : Screen