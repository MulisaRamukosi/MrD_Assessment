package com.example.mrd_assessment.data.menu.di

import com.example.mrd_assessment.data.menu.usecase.RequestRestaurantMenuUseCase
import com.example.mrd_assessment.data.menu.usecase.RequestRestaurantMenuUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MenuDataModule {

    @Binds
    @Singleton
    internal abstract fun bindRequestRestaurantMenuUseCase(
        impl: RequestRestaurantMenuUseCaseImpl
    ): RequestRestaurantMenuUseCase
}
