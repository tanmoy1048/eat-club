package com.eatclubapplication.data.repository

import com.eatclubapplication.data.model.Restaurant
import com.eatclubapplication.data.remote.EatClubApiService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val apiService: EatClubApiService
) {

    suspend fun getRestaurants(): com.eatclubapplication.data.model.RestaurantList {
        return apiService.getRestaurants()
    }

    suspend fun getRestaurantById(id: String): Restaurant? {
        return apiService.getRestaurants().restaurants.find { it.objectId == id }
    }
}
