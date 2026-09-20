package com.example.mrd_assessment.feature.home.restauranttab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mrd_assessment.data.restaurant.usecase.RequestRestaurantUseCase
import com.example.mrd_assessment.data.restaurant.usecase.SetRestaurantAsFavouriteUseCase
import com.example.mrd_assessment.model.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantTabViewModel @Inject constructor(
    private val requestRestaurantUseCase: RequestRestaurantUseCase,
    private val setRestaurantAsFavouriteUseCase: SetRestaurantAsFavouriteUseCase
) : ViewModel() {

    val restaurantsPagingData: Flow<PagingData<Restaurant>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { RestaurantPagingSource(requestRestaurantUseCase) }
    ).flow.cachedIn(viewModelScope)

    private val _favouriteRestaurantIds = MutableStateFlow<Set<String>>(emptySet())
    val favouriteRestaurantIds: StateFlow<Set<String>> = _favouriteRestaurantIds.asStateFlow()

    private val _loadingFavouriteIds = MutableStateFlow<Set<String>>(emptySet())
    val loadingFavouriteIds: StateFlow<Set<String>> = _loadingFavouriteIds.asStateFlow()

    fun setFavourite(restaurantId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            _loadingFavouriteIds.update { it + restaurantId }

            val result = setRestaurantAsFavouriteUseCase(restaurantId)

            if (!result.requestFailed()) {
                _favouriteRestaurantIds.update { current ->
                    if (isFavourite) current + restaurantId else current - restaurantId
                }
            }

            _loadingFavouriteIds.update { it - restaurantId }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
