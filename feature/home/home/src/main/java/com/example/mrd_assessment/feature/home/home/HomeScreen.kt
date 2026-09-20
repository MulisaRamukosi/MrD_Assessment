package com.example.mrd_assessment.feature.home.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRestaurantClick: (String) -> Unit = {}
) {
    Text(
        text = "Home Screen",
        modifier = modifier
    )
}
