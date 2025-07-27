package com.eatclubapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Deal(
    val objectId: String,
    val discount: Int,
    val qtyLeft: Int,
    val dineIn: Boolean,
    val lightning: Boolean? = null,
    val open: String? = null,
    val close: String? = null,
    val start: String? = null,
    val end: String? = null,
)
