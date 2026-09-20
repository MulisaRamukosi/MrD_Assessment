package com.example.mrd_assessment.core.datastore.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mrd_assessment.core.datastore.db.entity.RestaurantRemoteKeyEntity

@Dao
interface RestaurantRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RestaurantRemoteKeyEntity>)

    @Query("SELECT * FROM restaurant_remote_keys WHERE restaurantId = :restaurantId")
    suspend fun getRemoteKeyByRestaurantId(restaurantId: String): RestaurantRemoteKeyEntity?

    @Query("SELECT lastUpdated FROM restaurant_remote_keys ORDER BY lastUpdated DESC LIMIT 1")
    suspend fun getLastUpdatedTime(): Long?

    @Query("DELETE FROM restaurant_remote_keys")
    suspend fun clearRemoteKeys()
}