package com.example.mrd_assessment.feature.home.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.mrd_assessment.core.string.R
import com.example.mrd_assessment.feature.home.favouritetab.RestaurantFavouriteTabScreen
import com.example.mrd_assessment.feature.home.restauranttab.RestaurantTabScreen

enum class HomeTab(
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    RESTAURANTS(
        titleRes = R.string.home_restaurant_tab_title,
        icon = Icons.Rounded.Restaurant
    ),
    FAVOURITES(
        titleRes = R.string.home_favourites_tab_title,
        icon = Icons.Rounded.Favorite
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRestaurantClick: (String) -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.RESTAURANTS) }
    val restaurantLazyListState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                HomeTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = stringResource(tab.titleRes)
                            )
                        },
                        label = {
                            Text(text = stringResource(tab.titleRes))
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                HomeTab.RESTAURANTS -> {
                    RestaurantTabScreen(
                        modifier = Modifier.fillMaxSize(),
                        lazyListState = restaurantLazyListState,
                        onRestaurantClick = onRestaurantClick
                    )
                }
                HomeTab.FAVOURITES -> {
                    RestaurantFavouriteTabScreen(
                        modifier = Modifier.fillMaxSize(),
                        onRestaurantClick = onRestaurantClick
                    )
                }
            }
        }
    }
}
