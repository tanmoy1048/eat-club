package com.eatclubapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val objectId: String,
    val name: String,
    val address1: String,
    val suburb: String,
    val cuisines: List<String>,
    val imageLink: String,
    val open: String,
    val close: String,
    val deals: List<Deal>
)

