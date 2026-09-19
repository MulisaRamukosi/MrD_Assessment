package com.example.mrd_assessment.data.restaurant.usecase

import com.backbase.deferredresources.DeferredText
import com.example.mrd_assessment.core.network.api.RestaurantApiService
import com.example.mrd_assessment.core.network.model.RequestResult
import jakarta.inject.Inject

interface SetRestaurantAsFavouriteUseCase {
    suspend operator fun invoke(restaurantId: String): RequestResult<Boolean>
}

internal class SetRestaurantAsFavouriteUseCaseImpl
    @Inject constructor(
        private val restaurantApiService: RestaurantApiService
    )
    : SetRestaurantAsFavouriteUseCase {
    override suspend fun invoke(restaurantId: String): RequestResult<Boolean> {
        val result = restaurantApiService.markRestaurantAsFavourite(restaurantId = restaurantId)

        return if (result.isSuccessful) {
            RequestResult(data = result.body())
        } else {
            RequestResult(message = DeferredText.Constant(value = result.message()))
        }
    }
}