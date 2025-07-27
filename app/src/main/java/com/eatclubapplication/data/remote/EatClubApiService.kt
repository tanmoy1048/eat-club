package com.eatclubapplication.data.remote

import com.eatclubapplication.data.model.Restaurant
import retrofit2.http.GET

interface EatClubApiService {

    @GET("/misc/challengedata.json")
    suspend fun getRestaurants(): com.eatclubapplication.data.model.RestaurantList
}
