package com.eatclubapplication.data.repository

import com.eatclubapplication.data.remote.EatClubApiService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val apiService: EatClubApiService
) {
    suspend fun getRestaurants() = apiService.getRestaurants()
}
