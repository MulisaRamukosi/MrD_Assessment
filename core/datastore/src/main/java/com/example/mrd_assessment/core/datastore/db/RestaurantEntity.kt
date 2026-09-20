package com.example.mrd_assessment.core.datastore.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mrd_assessment.model.Restaurant

@Entity(tableName = "restaurants")
data class RestaurantEntity(
    @PrimaryKey
    val id: String,
    val name: String?,
    val cuisines: List<String>,
    val rating: Float?,
    val deliveryFeeCents: Int?,
    val etaMinutes: Int?,
    val isOpen: Boolean?,
    val imageUrl: String?
)

fun Restaurant.toEntity(): RestaurantEntity = RestaurantEntity(
    id = id,
    name = name,
    cuisines = cuisines,
    rating = rating,
    deliveryFeeCents = deliveryFeeCents,
    etaMinutes = etaMinutes,
    isOpen = isOpen,
    imageUrl = imageUrl
)

fun RestaurantEntity.toDomainModel(): Restaurant = Restaurant(
    id = id,
    name = name,
    cuisines = cuisines,
    rating = rating,
    deliveryFeeCents = deliveryFeeCents,
    etaMinutes = etaMinutes,
    isOpen = isOpen,
    imageUrl = imageUrl
)
