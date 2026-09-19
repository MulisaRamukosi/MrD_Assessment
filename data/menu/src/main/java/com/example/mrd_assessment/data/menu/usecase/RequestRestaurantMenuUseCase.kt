package com.example.mrd_assessment.data.menu.usecase

import com.example.mrd_assessment.core.network.api.MenuApiService
import com.example.mrd_assessment.core.network.model.RequestResult
import com.example.mrd_assessment.model.Menu
import jakarta.inject.Inject

interface RequestRestaurantMenuUseCase {
    suspend operator fun invoke(restaurantId: String): RequestResult<Menu>
}

internal class RequestRestaurantMenuUseCaseImpl
    @Inject constructor(
        private val menuApiService: MenuApiService
    ): RequestRestaurantMenuUseCase {
    override suspend fun invoke(restaurantId: String): RequestResult<Menu> {
        val result = menuApiService.getMenu(restaurantId = restaurantId)

        TODO("Not yet implemented")
    }

}