package com.example.mrd_assessment.core.datastore.db.entity

import com.example.mrd_assessment.model.Restaurant

abstract class BaseRestaurantEntity<T>(
    open val id: String,
    open val name: String?,
    open val cuisines: List<String>,
    open val rating: Float?,
    open val deliveryFeeCents: Int?,
    open val etaMinutes: Int?,
    open val isOpen: Boolean?,
    open val imageUrl: String?
) {
    fun toDomainModel(): Restaurant = Restaurant(
        id = id,
        name = name,
        cuisines = cuisines,
        rating = rating,
        deliveryFeeCents = deliveryFeeCents,
        etaMinutes = etaMinutes,
        isOpen = isOpen,
        imageUrl = imageUrl
    )

}

fun Restaurant.toRestaurantEntity(): RestaurantEntity = RestaurantEntity(
    id = id,
    name = name,
    cuisines = cuisines,
    rating = rating,
    deliveryFeeCents = deliveryFeeCents,
    etaMinutes = etaMinutes,
    isOpen = isOpen,
    imageUrl = imageUrl
)

fun Restaurant.toFavouriteRestaurantEntity(): FavouriteRestaurantEntity = FavouriteRestaurantEntity(
    id = id,
    name = name,
    cuisines = cuisines,
    rating = rating,
    deliveryFeeCents = deliveryFeeCents,
    etaMinutes = etaMinutes,
    isOpen = isOpen,
    imageUrl = imageUrl
)
