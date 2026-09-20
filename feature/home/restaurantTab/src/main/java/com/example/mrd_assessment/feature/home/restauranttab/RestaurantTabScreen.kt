package com.example.mrd_assessment.feature.home.restauranttab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.mrd_assessment.feature.home.common.RestaurantItem
import kotlinx.coroutines.flow.distinctUntilChanged
import com.example.mrd_assessment.core.string.R as StringR

@Composable
fun RestaurantTabScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    viewModel: RestaurantTabViewModel = hiltViewModel(),
    onRestaurantClick: (String) -> Unit = {}
) {
    val lazyPagingItems = viewModel.restaurantsPagingData.collectAsLazyPagingItems()
    val favouriteIds by viewModel.favouriteRestaurantIds.collectAsState()
    val loadingFavouriteIds by viewModel.loadingFavouriteIds.collectAsState()
    val scrollPosition by viewModel.scrollPosition.collectAsState()

    var isScrollRestored by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(lazyPagingItems.itemCount) {
        if (!isScrollRestored && lazyPagingItems.itemCount > 0) {
            if (scrollPosition.index < lazyPagingItems.itemCount) {
                lazyListState.scrollToItem(scrollPosition.index, scrollPosition.offset)
            }
            isScrollRestored = true
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex to lazyListState.firstVisibleItemScrollOffset }
            .distinctUntilChanged()
            .collect { (index, offset) ->
                if (isScrollRestored) {
                    viewModel.saveScrollPosition(index, offset)
                }
            }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyListState,
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
                        isFavourite = favouriteIds.contains(restaurant.id),
                        isLoadingFavouriteState = loadingFavouriteIds.contains(restaurant.id),
                        onRestaurantClick = onRestaurantClick,
                        onSetFavouriteState = { restaurantId, isFav ->
                            viewModel.setFavourite(restaurantId, isFav)
                        }
                    )
                }
            }

            when (val appendState = lazyPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    val error = appendState.error as? LoadFailedException

                    item {
                        ErrorView(
                            error = error, onClickRetry = { lazyPagingItems.retry() }
                        )

                    }
                }

                else -> {}
            }
        }

        when (val refreshState = lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                if (lazyPagingItems.itemCount == 0) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is LoadState.Error -> {
                if (lazyPagingItems.itemCount == 0) {
                    val error = refreshState.error as? LoadFailedException

                    ErrorView(error = error, onClickRetry = { lazyPagingItems.retry() })
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun ErrorView(
    error: LoadFailedException?,
    onClickRetry: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
    ) {
        Text(
            text = error?.errorMessage?.resolve(context = context)?.toString()
                ?: stringResource(StringR.string.common_network_error_msg),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        Button(
            content = {
                Text(text = stringResource(id = StringR.string.common_try_again))
            },
            onClick = onClickRetry
        )
    }
}
