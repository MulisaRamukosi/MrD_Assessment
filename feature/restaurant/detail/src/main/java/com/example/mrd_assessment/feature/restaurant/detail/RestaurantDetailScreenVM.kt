package com.example.mrd_assessment.feature.restaurant.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mrd_assessment.core.ui.model.State
import com.example.mrd_assessment.core.ui.model.ViewStateContainer
import com.example.mrd_assessment.data.menu.usecase.RequestRestaurantMenuUseCase
import com.example.mrd_assessment.model.Menu
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = RestaurantDetailScreenVM.Factory::class)
class RestaurantDetailScreenVM @AssistedInject constructor(
    private val requestRestaurantMenuUseCase: RequestRestaurantMenuUseCase,
    @Assisted private val restaurantId: String,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(restaurantId: String): RestaurantDetailScreenVM
    }

    private val _viewStateContainer = MutableStateFlow(
        ViewStateContainer(
            viewState = RestaurantDetailScreenState(),
            uiState = State.LOADING
        )
    )
    val viewStateContainer: StateFlow<ViewStateContainer<RestaurantDetailScreenState>> =
        _viewStateContainer.asStateFlow()

    init {
        loadMenu()
    }

    fun loadMenu() {
        viewModelScope.launch {
            _viewStateContainer.update {
                it.copy(uiState = State.LOADING, message = null)
            }

            val result = requestRestaurantMenuUseCase(restaurantId = restaurantId)

            if (!result.requestFailed() && result.data != null) {
                _viewStateContainer.update { container ->
                    container.copy(
                        viewState = RestaurantDetailScreenState(menu = result.data ?: Menu.EMPTY),
                        uiState = State.SUCCESS,
                        isInitialLoad = false
                    )
                }
            } else {
                _viewStateContainer.update { container ->
                    container.copy(
                        uiState = State.FAILED,
                        message = result.message,
                        isInitialLoad = false
                    )
                }
            }
        }
    }
}
