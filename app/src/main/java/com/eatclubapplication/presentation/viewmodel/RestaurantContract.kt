package com.eatclubapplication.presentation.viewmodel

import com.eatclubapplication.data.model.Deal
import com.eatclubapplication.data.model.Restaurant

interface RestaurantContract {
    data class RestaurantListViewState(
        val restaurants: List<Restaurant> = emptyList(),
        val filteredRestaurants: List<Restaurant> = emptyList(),
        val isLoading: Boolean = false,
        val searchQuery: String = "",
        val showBottomSheet: Boolean = false,
        val sortOption: SortOption = SortOption.BEST_DEALS
    )

    sealed class Actions {
        data class ShowSnackBarMessage(val messageText: String) : Actions()
    }
}



data class RestaurantDetailViewState(
    val restaurant: Restaurant? = null,
    val deals: List<Deal> = emptyList()
)