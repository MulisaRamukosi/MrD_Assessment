package com.example.mrd_assessment.core.datastore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_remote_keys")
data class RestaurantRemoteKeyEntity(
    @PrimaryKey
    val restaurantId: String,
    val prevKey: Int?,
    val nextKey: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)
