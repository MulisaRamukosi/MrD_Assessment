package com.example.mrd_assessment.feature.restaurant.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RestaurantDetailScreen(
    restaurantId: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    Text(
        text = "Restaurant Detail Screen for ID: $restaurantId",
        modifier = modifier
    )
}
