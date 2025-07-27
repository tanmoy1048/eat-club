package com.eatclubapplication.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eatclubapplication.data.model.Deal
import com.eatclubapplication.presentation.viewmodel.RestaurantListViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailScreen(
    navController: NavController,
    viewModel: RestaurantListViewModel = hiltViewModel()
) {
    val viewState by viewModel.detailViewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(viewState.restaurant?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        padding ->
        Column(modifier = Modifier.padding(padding)) {
            viewState.restaurant?.let {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = it.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = it.cuisines.joinToString(", "))
                    //Text(text = "${it.rating} stars")
                    Text(text = it.address1)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Deals", style = MaterialTheme.typography.headlineMedium)
                    LazyColumn {
                        items(viewState.deals) {
                            DealCard(deal = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DealCard(deal: Deal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            //Text(text = deal.title, style = MaterialTheme.typography.headlineSmall)
            //Text(text = deal.description)
            Text(text = "${deal.discount}% off")
        }
    }
}
