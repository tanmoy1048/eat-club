package com.eatclubapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eatclubapplication.data.model.Deal
import com.eatclubapplication.data.model.Restaurant
import com.eatclubapplication.data.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortOption {
    NAME,
    BEST_DEALS
}

@HiltViewModel
class RestaurantListViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(RestaurantContract.RestaurantListViewState())
    val viewState = _viewState.asStateFlow()

    private val _detailViewState = MutableStateFlow(RestaurantDetailViewState())
    val detailViewState = _detailViewState.asStateFlow()

    private val _actions = MutableSharedFlow<RestaurantContract.Actions>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    internal val actions: Flow<RestaurantContract.Actions> = _actions.asSharedFlow()

    init {
        fetchRestaurants()
    }

    private fun fetchRestaurants() {
        viewModelScope.launch {
            try {
                _viewState.update {
                    it.copy(isLoading = true)
                }
                val restaurantList = repository.getRestaurants()
                val sortedList = restaurantList.restaurants.sortedByDescending { it.deals.maxOfOrNull { deal -> deal.discount } ?: 0 }
                _viewState.update {
                    it.copy(
                        restaurants = sortedList,
                        filteredRestaurants = sortedList,
                        isLoading = false,
                        sortOption = SortOption.BEST_DEALS
                    )
                }
            } catch (e: Exception) {
                Log.d("TAG", "fetchRestaurants: ${e.message}")
                showSnackBarMessage("Something went wrong")
                _viewState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _viewState.update {
            it.copy(searchQuery = query)
        }
        filterRestaurants()
    }

    private fun filterRestaurants() {
        _viewState.update {
            val filtered = if (it.searchQuery.isBlank()) {
                it.restaurants
            } else {
                it.restaurants.filter { restaurant ->
                    restaurant.name.contains(it.searchQuery, ignoreCase = true) ||
                            restaurant.cuisines.any { cuisine ->
                                cuisine.contains(it.searchQuery, ignoreCase = true)
                            }
                }
            }
            it.copy(filteredRestaurants = filtered)
        }
    }

    fun sortByName() {
        _viewState.update {
            val sortedList = it.restaurants.sortedBy { it.name }
            it.copy(restaurants = sortedList, sortOption = SortOption.NAME)
        }
        filterRestaurants()
    }

    fun sortByBestDeals() {
        _viewState.update {
            val sortedList = it.restaurants.sortedByDescending { it.deals.maxOfOrNull { deal -> deal.discount } ?: 0 }
            it.copy(restaurants = sortedList, sortOption = SortOption.BEST_DEALS)
        }
        filterRestaurants()
    }

    fun onRestaurantSelected(restaurant: Restaurant) {
        _detailViewState.value = RestaurantDetailViewState(
            restaurant = restaurant,
            deals = restaurant.deals.sortedByDescending { it.discount }
        )
    }

    fun onSettingsClicked(show: Boolean) {
        _viewState.update {
            it.copy(showBottomSheet = show)
        }
    }

    private fun showSnackBarMessage(message: String) {
        viewModelScope.launch {
            _actions.emit(RestaurantContract.Actions.ShowSnackBarMessage(message))
        }
    }
}
