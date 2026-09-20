package com.example.mrd_assessment.data.restaurant.usecase

import com.example.mrd_assessment.core.datastore.db.dao.FavouriteRestaurantDao
import com.example.mrd_assessment.core.datastore.db.entity.toFavouriteRestaurantEntity
import com.example.mrd_assessment.core.network.model.RequestResult
import com.example.mrd_assessment.model.Restaurant
import javax.inject.Inject

interface SetRestaurantAsFavouriteUseCase {
    suspend operator fun invoke(restaurant: Restaurant, isFavourite: Boolean): RequestResult<Boolean>
}

internal class SetRestaurantAsFavouriteUseCaseImpl @Inject constructor(
    private val favouriteRestaurantDao: FavouriteRestaurantDao
) : SetRestaurantAsFavouriteUseCase {
    override suspend fun invoke(restaurant: Restaurant, isFavourite: Boolean): RequestResult<Boolean> {
        if (isFavourite) {
            favouriteRestaurantDao.insert(restaurant = restaurant.toFavouriteRestaurantEntity())
        } else {
            favouriteRestaurantDao.delete(restaurant.toFavouriteRestaurantEntity())
        }
        return RequestResult(data = true)
    }
}
