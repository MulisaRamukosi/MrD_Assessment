package com.example.mrd_assessment.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Menu(
    @SerialName("restaurant_id")
    val restaurantId: String,
    @SerialName("categories")
    val categories: List<Category>
)

@Serializable
data class Category(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String?,
    @SerialName("items")
    val items: List<Item>
)

@Serializable
data class Item(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String?,
    @SerialName("price_cents")
    val priceCents: Int?,
    @SerialName("available")
    val isAvailable: Boolean?,
    @SerialName("description")
    val description: String?,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("options")
    val options: List<Option>
)

@Serializable
data class Option(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String?,
    @SerialName("price_cents")
    val priceCents: Int?,
)
