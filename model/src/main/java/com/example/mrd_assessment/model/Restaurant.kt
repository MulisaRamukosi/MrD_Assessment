package com.example.mrd_assessment.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String?,
    @SerialName("cuisines")
    val cuisines: List<String>,
    @SerialName("rating")
    val rating: Float?,
    @SerialName("delivery_fee_cents")
    val deliveryFeeCents: Int?,
    @SerialName("eta_minutes")
    val etaMinutes: Int?,
    @SerialName("is_open")
    val isOpen: Boolean?,
    @SerialName("image_url")
    val imageUrl: String?,
) {
}
