package com.example.mrd_assessment.core.datastore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [RestaurantEntity::class, RestaurantRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CuisinesTypeConverter::class)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun remoteKeysDao(): RestaurantRemoteKeysDao
}
