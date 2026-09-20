package com.example.mrd_assessment.core.datastore.di

import android.content.Context
import androidx.room.Room
import com.example.mrd_assessment.core.datastore.db.RestaurantDatabase
import com.example.mrd_assessment.core.datastore.db.dao.FavouriteRestaurantDao
import com.example.mrd_assessment.core.datastore.db.dao.RestaurantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRestaurantDatabase(
        @ApplicationContext context: Context
    ): RestaurantDatabase {
        return Room.databaseBuilder(
            context,
            RestaurantDatabase::class.java,
            "restaurant_database"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }

    @Provides
    fun provideRestaurantDao(database: RestaurantDatabase): RestaurantDao {
        return database.restaurantDao()
    }

    @Provides
    fun provideFavouriteRestaurantDao(database: RestaurantDatabase): FavouriteRestaurantDao {
        return database.favouriteRestaurantDao()
    }
}
