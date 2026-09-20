package com.example.mrd_assessment.feature.home.favouritetab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.mrd_assessment.core.string.R as StringR
import com.example.mrd_assessment.feature.home.common.RestaurantItem

@Composable
fun RestaurantFavouriteTabScreen(
    modifier: Modifier = Modifier,
    viewModel: RestaurantFavouriteTabViewModel = hiltViewModel(),
    onRestaurantClick: (String) -> Unit = {}
) {
    val lazyPagingItems = viewModel.favouriteRestaurantsPagingData.collectAsLazyPagingItems()

    Box(modifier = modifier.fillMaxSize()) {
        if (lazyPagingItems.itemCount == 0) {
            Text(
                text = stringResource(id = StringR.string.favourite_no_items_msg),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(space = 8.dp)
            ) {
                items(
                    count = lazyPagingItems.itemCount,
                    key = lazyPagingItems.itemKey { it.id }
                ) { index ->
                    val restaurant = lazyPagingItems[index]
                    if (restaurant != null) {
                        RestaurantItem(
                            modifier = Modifier.fillMaxWidth(),
                            restaurant = restaurant,
                            isFavourite = true,
                            isLoadingFavouriteState = false,
                            onRestaurantClick = onRestaurantClick,
                            onSetFavouriteState = { isFav ->
                                viewModel.toggleFavourite(restaurant, isFav)
                            }
                        )
                    }
                }
            }
        }
    }
}
