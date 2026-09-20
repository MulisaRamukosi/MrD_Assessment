package com.example.mrd_assessment.feature.home.restauranttab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mrd_assessment.core.datastore.preferences.ScrollPosition
import com.example.mrd_assessment.core.datastore.preferences.ScrollPositionPreferences
import com.example.mrd_assessment.data.restaurant.usecase.GetRestaurantsPagerUseCase
import com.example.mrd_assessment.data.restaurant.usecase.SetRestaurantAsFavouriteUseCase
import com.example.mrd_assessment.model.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantTabViewModel @Inject constructor(
    getRestaurantsPagerUseCase: GetRestaurantsPagerUseCase,
    private val setRestaurantAsFavouriteUseCase: SetRestaurantAsFavouriteUseCase,
    private val scrollPositionPreferences: ScrollPositionPreferences
) : ViewModel() {

    val restaurantsPagingData: Flow<PagingData<Restaurant>> = getRestaurantsPagerUseCase(PAGE_SIZE)
        .cachedIn(viewModelScope)

    val scrollPosition: StateFlow<ScrollPosition> = scrollPositionPreferences.scrollPosition
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScrollPosition()
        )

    private val _favouriteRestaurantIds = MutableStateFlow<Set<String>>(emptySet())
    val favouriteRestaurantIds: StateFlow<Set<String>> = _favouriteRestaurantIds.asStateFlow()

    private val _loadingFavouriteIds = MutableStateFlow<Set<String>>(emptySet())
    val loadingFavouriteIds: StateFlow<Set<String>> = _loadingFavouriteIds.asStateFlow()

    fun saveScrollPosition(index: Int, offset: Int) {
        viewModelScope.launch {
            scrollPositionPreferences.saveScrollPosition(index, offset)
        }
    }

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
