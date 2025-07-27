package com.eatclubapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantList(
    val restaurants: List<Restaurant>
)
