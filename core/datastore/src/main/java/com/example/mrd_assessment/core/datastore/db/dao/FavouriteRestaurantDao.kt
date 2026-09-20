package com.example.mrd_assessment.core.datastore.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mrd_assessment.core.datastore.db.entity.FavouriteRestaurantEntity
import com.example.mrd_assessment.core.datastore.db.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurant: FavouriteRestaurantEntity)

    @Query("SELECT * FROM favouriteRestaurant")
    fun getFavouriteRestaurantPagingSource(): PagingSource<Int, FavouriteRestaurantEntity>

    @Query("SELECT id FROM favouriteRestaurant")
    fun getFavouriteRestaurantIds(): Flow<List<String>>

    @Delete
    fun delete(restaurant: FavouriteRestaurantEntity)
}