package com.example.mrd_assessment.data.restaurant.di

import com.example.mrd_assessment.data.restaurant.usecase.RequestRestaurantUseCase
import com.example.mrd_assessment.data.restaurant.usecase.RequestRestaurantUseCaseImpl
import com.example.mrd_assessment.data.restaurant.usecase.SetRestaurantAsFavouriteUseCase
import com.example.mrd_assessment.data.restaurant.usecase.SetRestaurantAsFavouriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RestaurantDataModule {

    @Binds
    @Singleton
    internal abstract fun bindRequestRestaurantUseCase(
        impl: RequestRestaurantUseCaseImpl
    ): RequestRestaurantUseCase

    @Binds
    @Singleton
    internal abstract fun bindSetRestaurantAsFavouriteUseCase(
        impl: SetRestaurantAsFavouriteUseCaseImpl
    ): SetRestaurantAsFavouriteUseCase
}
