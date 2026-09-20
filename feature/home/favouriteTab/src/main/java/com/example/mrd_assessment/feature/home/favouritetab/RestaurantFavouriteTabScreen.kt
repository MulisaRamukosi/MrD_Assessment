package com.example.mrd_assessment.feature.home.favouritetab

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RestaurantFavouriteTabScreen(
    modifier: Modifier = Modifier,
    onRestaurantClick: (String) -> Unit = {}
) {
    Text(
        text = "Restaurant Favourite Tab",
        modifier = modifier
    )
}
