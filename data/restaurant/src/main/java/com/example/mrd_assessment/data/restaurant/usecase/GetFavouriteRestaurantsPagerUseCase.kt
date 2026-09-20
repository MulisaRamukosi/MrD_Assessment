package com.example.mrd_assessment.data.restaurant.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mrd_assessment.core.datastore.db.dao.FavouriteRestaurantDao
import com.example.mrd_assessment.model.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetFavouriteRestaurantsPagerUseCase {
    operator fun invoke(pageSize: Int): Flow<PagingData<Restaurant>>
}

internal class GetFavouriteRestaurantsPagerUseCaseImpl @Inject constructor(
    private val favouriteRestaurantDao: FavouriteRestaurantDao
) : GetFavouriteRestaurantsPagerUseCase {

    override fun invoke(pageSize: Int): Flow<PagingData<Restaurant>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { favouriteRestaurantDao.getFavouriteRestaurantPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomainModel() }
        }
    }
}
