package com.eatclubapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eatclubapplication.presentation.ui.RestaurantDetailScreen
import com.eatclubapplication.presentation.ui.RestaurantListScreen
import com.eatclubapplication.presentation.viewmodel.RestaurantListViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: RestaurantListViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = "restaurant_list") {
        composable("restaurant_list") {
            RestaurantListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            "restaurant_detail"
        ) { backStackEntry ->
            RestaurantDetailScreen(navController = navController, viewModel = viewModel)
        }
    }
}
