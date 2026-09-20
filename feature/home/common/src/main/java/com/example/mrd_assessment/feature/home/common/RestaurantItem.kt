package com.example.mrd_assessment.feature.home.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeliveryDining
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mrd_assessment.core.ui.theme.MrD_AssessmentTheme
import com.example.mrd_assessment.model.Restaurant
import com.example.mrd_assessment.core.string.R as StringR

@Composable
fun RestaurantItem(
    modifier: Modifier = Modifier,
    restaurant: Restaurant,
    isFavourite: Boolean,
    isLoadingFavouriteState: Boolean,
    onRestaurantClick: (String) -> Unit,
    onSetFavouriteState: (Boolean) -> Unit
) {
    Card(modifier = modifier, onClick = { onRestaurantClick(restaurant.id) }) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth().height(height = 200.dp),
                    contentScale = ContentScale.Crop,
                    model = restaurant.imageUrl,
                    contentDescription = null
                )

                RestaurantDetails(
                    modifier = Modifier.fillMaxWidth().padding(all = 8.dp),
                    restaurant = restaurant
                )
            }

            FilledTonalIconToggleButton(
                checked = isFavourite,
                enabled = !isLoadingFavouriteState,
                onCheckedChange = {
                    onSetFavouriteState(it)
                },
                modifier = Modifier.align(alignment = Alignment.TopEnd).padding(all = 4.dp)
            ) {
                if (isLoadingFavouriteState) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(size = 20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = if (isFavourite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun RestaurantDetails(modifier: Modifier, restaurant: Restaurant) {
    Column(modifier = modifier) {
        Text(text = restaurant.name.orEmpty(), style = MaterialTheme.typography.titleLarge)

        Text(
            text = restaurant.cuisines.joinToString(", "),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(height = 4.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(space = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            RateItem(rate = restaurant.rating ?: 5f)

            DeliveryItem(
                deliveryFeeCents = restaurant.deliveryFeeCents ?: 0,
                etaMinutes = restaurant.etaMinutes.toString()
            )

            Spacer(modifier = Modifier.weight(weight = 1f))
            StoreOperatingState(isOpen = restaurant.isOpen ?: false)
        }
    }

}

@Composable
private fun RateItem(
    rate: Float,
) {
    val bgColor = when {
        rate < 1.6f -> Color.Red
        rate < 3.2f -> Color(0xFFFFA500)
        else -> Color(0xFF066417)
    }

    Card(colors = CardDefaults.cardColors(containerColor = bgColor, contentColor = Color.White)) {
        Row(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 2.dp)
        ) {
            Icon(
                modifier = Modifier.size(size = 12.dp),
                imageVector = Icons.Rounded.StarRate,
                contentDescription = null
            )
            Text(text = rate.toString(), style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun DeliveryItem(
    deliveryFeeCents: Int,
    etaMinutes: String
) {
    val formattedDeliveryFee = remember(deliveryFeeCents) {
        deliveryFeeCents / 100
    }
    val deliveryFee = if (formattedDeliveryFee == 0) {
        stringResource(id = StringR.string.common_free)
    } else {
        "R$formattedDeliveryFee"
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 2.dp)
        ) {
            Icon(
                modifier = Modifier.size(size = 12.dp),
                imageVector = Icons.Rounded.DeliveryDining,
                contentDescription = null
            )
            Text(
                text = stringResource(
                    id = StringR.string.frmt_delivery_info,
                    deliveryFee,
                    etaMinutes),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun StoreOperatingState(
    isOpen: Boolean
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isOpen) {
                Color(0xFF066417)
            } else {
                Color.Red
            },
            contentColor = Color.White
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
            text = stringResource(
                id = if (isOpen) {
                    StringR.string.common_open
                } else {
                    StringR.string.common_closed
                }
            ),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
fun RestaurantItemDefaultPreview() {
    MrD_AssessmentTheme {
        RestaurantItem(
            modifier = Modifier,
            restaurant = Restaurant(
                id = "r-1001",
                name = "Cape Town Grill",
                cuisines = listOf("Steakhouse", "Grill"),
                rating = 4.6f,
                deliveryFeeCents = 1500,
                etaMinutes = 32,
                isOpen = true,
                imageUrl = "https://picsum.photos/seed/r1001/400/300"
            ),
            isFavourite = false,
            isLoadingFavouriteState = false,
            onRestaurantClick = {},
            onSetFavouriteState = { }
        )
    }
}
