package com.eatclubapplication.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eatclubapplication.presentation.viewmodel.RestaurantContract
import com.eatclubapplication.presentation.viewmodel.RestaurantListViewModel
import com.eatclubapplication.presentation.viewmodel.SortOption
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantListScreen(
    navController: NavController,
    viewModel: RestaurantListViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Restaurants") },
                actions = {
                    IconButton(onClick = { viewModel.onSettingsClicked(true) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "e.g. chinese, pizza"
                    )
                },
                value = viewState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            if (viewState.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewState.filteredRestaurants) {
                        RestaurantCard(restaurant = it, onCardClick = {
                            viewModel.onRestaurantSelected(it)
                            navController.navigate("restaurant_detail")
                        })
                    }
                }
            }
        }
    }

    if (viewState.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onSettingsClicked(false) },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SortOptionItem(
                    text = "Sort by Name",
                    isSelected = viewState.sortOption == SortOption.NAME,
                    onClick = {
                        viewModel.sortByName()
                        viewModel.onSettingsClicked(false)
                    }
                )
                SortOptionItem(
                    text = "Sort by Best Deals",
                    isSelected = viewState.sortOption == SortOption.BEST_DEALS,
                    onClick = {
                        viewModel.sortByBestDeals()
                        viewModel.onSettingsClicked(false)
                    }
                )
            }
        }
    }

    LaunchedEffect(viewModel.actions) {
        viewModel.actions.collectLatest {
            when (it) {
                is RestaurantContract.Actions.ShowSnackBarMessage -> {
                    snackbarHostState.showSnackbar(
                        message = it.messageText,
                    )
                }
            }
        }
    }
}

@Composable
fun SortOptionItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = text, modifier = Modifier.weight(1.0f))
        Spacer(Modifier.width(8.dp))
        if (isSelected) {
            Icon(Icons.Default.Check, contentDescription = "Selected")
        }
    }
}

