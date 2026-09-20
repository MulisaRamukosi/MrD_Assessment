package com.example.mrd_assessment.data.restaurant.usecase

import com.example.mrd_assessment.core.datastore.db.dao.FavouriteRestaurantDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetFavouriteRestaurantIdsUseCase {
    operator fun invoke(): Flow<Set<String>>
}

internal class GetFavouriteRestaurantIdsUseCaseImpl @Inject constructor(
    private val favouriteRestaurantDao: FavouriteRestaurantDao
) : GetFavouriteRestaurantIdsUseCase {
    override fun invoke(): Flow<Set<String>> {
        return favouriteRestaurantDao.getFavouriteRestaurantIds().map { it.toSet() }
    }
}
