package com.example.mrd_assessment.data.restaurant.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mrd_assessment.core.datastore.db.RestaurantDatabase
import com.example.mrd_assessment.core.datastore.preferences.ScrollPositionPreferences
import com.example.mrd_assessment.data.restaurant.mediator.RestaurantRemoteMediator
import com.example.mrd_assessment.model.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetRestaurantsPagerUseCase {
    operator fun invoke(pageSize: Int): Flow<PagingData<Restaurant>>
}

internal class GetRestaurantsPagerUseCaseImpl @Inject constructor(
    private val database: RestaurantDatabase,
    private val requestRestaurantUseCase: RequestRestaurantUseCase,
    private val scrollPositionPreferences: ScrollPositionPreferences
) : GetRestaurantsPagerUseCase {

    @OptIn(ExperimentalPagingApi::class)
    override fun invoke(pageSize: Int): Flow<PagingData<Restaurant>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            remoteMediator = RestaurantRemoteMediator(
                database = database,
                requestRestaurantUseCase = requestRestaurantUseCase,
                scrollPositionPreferences = scrollPositionPreferences,
            ),
            pagingSourceFactory = { database.restaurantDao().getRestaurantsPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomainModel() }
        }
    }
}
