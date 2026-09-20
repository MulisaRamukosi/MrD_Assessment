package com.example.mrd_assessment.feature.home.restauranttab

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.backbase.deferredresources.DeferredText
import com.example.mrd_assessment.data.restaurant.usecase.RequestRestaurantUseCase
import com.example.mrd_assessment.model.Restaurant

class RestaurantPagingSource(
    private val requestRestaurantUseCase: RequestRestaurantUseCase
) : PagingSource<Int, Restaurant>() {

    override fun getRefreshKey(state: PagingState<Int, Restaurant>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Restaurant> {
        val page = params.key ?: STARTING_PAGE_INDEX

        val result = requestRestaurantUseCase(page = page)

        return if (!result.requestFailed() && result.data != null) {
            val pageResult = result.data!!
            val restaurants = pageResult.data
            val nextKey = if (pageResult.hasMorePages) page + 1 else null
            val prevKey = if (page > STARTING_PAGE_INDEX) page - 1 else null

            LoadResult.Page(
                data = restaurants,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } else {
            LoadResult.Error(
                LoadFailedException(
                    errorMessage = result.message
                )
            )
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}

data class LoadFailedException(
    val errorMessage: DeferredText?
) : Exception()