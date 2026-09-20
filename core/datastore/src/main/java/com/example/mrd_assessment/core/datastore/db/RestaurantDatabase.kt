package com.example.mrd_assessment.core.datastore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mrd_assessment.core.datastore.db.dao.FavouriteRestaurantDao
import com.example.mrd_assessment.core.datastore.db.dao.RestaurantDao
import com.example.mrd_assessment.core.datastore.db.dao.RestaurantRemoteKeysDao
import com.example.mrd_assessment.core.datastore.db.entity.FavouriteRestaurantEntity
import com.example.mrd_assessment.core.datastore.db.entity.RestaurantEntity
import com.example.mrd_assessment.core.datastore.db.entity.RestaurantRemoteKeyEntity

@Database(
    entities = [RestaurantEntity::class, RestaurantRemoteKeyEntity::class, FavouriteRestaurantEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CuisinesTypeConverter::class)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun remoteKeysDao(): RestaurantRemoteKeysDao

    abstract fun favouriteRestaurantDao(): FavouriteRestaurantDao
}
