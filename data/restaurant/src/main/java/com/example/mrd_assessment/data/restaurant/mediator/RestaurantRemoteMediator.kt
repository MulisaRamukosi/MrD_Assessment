package com.example.mrd_assessment.data.restaurant.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mrd_assessment.core.datastore.db.RestaurantDatabase
import com.example.mrd_assessment.core.datastore.db.entity.RestaurantEntity
import com.example.mrd_assessment.core.datastore.db.entity.RestaurantRemoteKeyEntity
import com.example.mrd_assessment.core.datastore.db.entity.toRestaurantEntity
import com.example.mrd_assessment.core.datastore.preferences.ScrollPositionPreferences
import com.example.mrd_assessment.data.restaurant.usecase.RequestRestaurantUseCase
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class RestaurantRemoteMediator(
    private val database: RestaurantDatabase,
    private val requestRestaurantUseCase: RequestRestaurantUseCase,
    private val scrollPositionPreferences: ScrollPositionPreferences
) : RemoteMediator<Int, RestaurantEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.HOURS.toMillis(1)
        val lastUpdated = database.remoteKeysDao().getLastUpdatedTime() ?: 0L
        val hasData = database.restaurantDao().hasRestaurants()

        return if (hasData && (lastUpdated == 0L || System.currentTimeMillis() - lastUpdated < cacheTimeout)) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RestaurantEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToPosition(state)

                if (remoteKeys == null) {
                    scrollPositionPreferences.clearStoredScrollPosition()
                }
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        val result = requestRestaurantUseCase(page = page)

        if (result.requestFailed() || result.data == null) {
            return MediatorResult.Error(
                Exception(result.message?.toString() ?: "Failed to load restaurants")
            )
        }

        val pageResult = result.data
        val restaurants = pageResult?.data ?: emptyList()
        val endOfPaginationReached = !(pageResult?.hasMorePages ?: false)

        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                database.remoteKeysDao().clearRemoteKeys()
                database.restaurantDao().clearAll()
            }

            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1

            val keys = restaurants.map { restaurant ->
                RestaurantRemoteKeyEntity(
                    restaurantId = restaurant.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }

            database.remoteKeysDao().insertAll(keys)
            database.restaurantDao().insertAll(restaurants.map { it.toRestaurantEntity() })
        }

        return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, RestaurantEntity>
    ): RestaurantRemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { restaurant ->
            database.remoteKeysDao().getRemoteKeyByRestaurantId(restaurant.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, RestaurantEntity>
    ): RestaurantRemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { restaurant ->
            database.remoteKeysDao().getRemoteKeyByRestaurantId(restaurant.id)
        }
    }

    private suspend fun getRemoteKeyClosestToPosition(
        state: PagingState<Int, RestaurantEntity>
    ): RestaurantRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { restaurantId ->
                database.remoteKeysDao().getRemoteKeyByRestaurantId(restaurantId)
            }
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
