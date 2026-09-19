package com.example.mrd_assessment.data.restaurant.usecase

import com.backbase.deferredresources.DeferredText
import com.example.mrd_assessment.core.network.api.GetRestaurantRequest
import com.example.mrd_assessment.core.network.api.RestaurantApiService
import com.example.mrd_assessment.core.network.model.PageResult
import com.example.mrd_assessment.core.network.model.RequestResult
import com.example.mrd_assessment.model.Restaurant
import jakarta.inject.Inject

interface RequestRestaurantUseCase {
    suspend operator fun invoke(page: Int): RequestResult<PageResult<Restaurant>>
}

internal class RequestRestaurantUseCaseImpl
    @Inject constructor(
        private val restaurantApiService: RestaurantApiService
    )
    : RequestRestaurantUseCase {
    override suspend fun invoke(page: Int): RequestResult<PageResult<Restaurant>> {
        val result = restaurantApiService.getRestaurants(
            request = GetRestaurantRequest(
                page = page
            )
        )

        return if (result.isSuccessful) {
            val body = result.body()

            RequestResult(
                data = PageResult(
                    data = body?.restaurants ?: emptyList(),
                    page = body?.page ?: 1,
                    hasMorePages = body?.hasMorePages ?: false
                )
            )
        } else {
            RequestResult(message = DeferredText.Constant(value = result.message()))
        }
    }

}