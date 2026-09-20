package com.example.mrd_assessment.core.datastore.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<RestaurantEntity>)

    @Query("SELECT * FROM restaurants")
    fun getRestaurantsPagingSource(): PagingSource<Int, RestaurantEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM restaurants)")
    suspend fun hasRestaurants(): Boolean

    @Query("DELETE FROM restaurants")
    suspend fun clearAll()
}
