package com.example.mrd_assessment.core.datastore.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteRestaurant")
class FavouriteRestaurantEntity(
    @PrimaryKey
    override val id: String,
    override val name: String?,
    override val cuisines: List<String>,
    override val rating: Float?,
    override val deliveryFeeCents: Int?,
    override val etaMinutes: Int?,
    override val isOpen: Boolean?,
    override val imageUrl: String?
) : BaseRestaurantEntity<FavouriteRestaurantEntity>(
    id = id,
    name = name,
    cuisines = cuisines,
    rating = rating,
    deliveryFeeCents = deliveryFeeCents,
    etaMinutes = etaMinutes,
    isOpen = isOpen,
    imageUrl = imageUrl
)