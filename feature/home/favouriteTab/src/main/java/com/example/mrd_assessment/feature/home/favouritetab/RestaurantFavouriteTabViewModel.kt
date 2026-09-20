package com.example.mrd_assessment.feature.home.favouritetab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mrd_assessment.data.restaurant.usecase.GetFavouriteRestaurantIdsUseCase
import com.example.mrd_assessment.data.restaurant.usecase.GetFavouriteRestaurantsPagerUseCase
import com.example.mrd_assessment.data.restaurant.usecase.SetRestaurantAsFavouriteUseCase
import com.example.mrd_assessment.model.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantFavouriteTabViewModel @Inject constructor(
    getFavouriteRestaurantsPagerUseCase: GetFavouriteRestaurantsPagerUseCase,
    private val setRestaurantAsFavouriteUseCase: SetRestaurantAsFavouriteUseCase
) : ViewModel() {

    val favouriteRestaurantsPagingData: Flow<PagingData<Restaurant>> =
        getFavouriteRestaurantsPagerUseCase(PAGE_SIZE).cachedIn(viewModelScope)

    fun toggleFavourite(restaurant: Restaurant, isFavourite: Boolean) {
        viewModelScope.launch(context = Dispatchers.IO) {
            setRestaurantAsFavouriteUseCase(restaurant, isFavourite)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
