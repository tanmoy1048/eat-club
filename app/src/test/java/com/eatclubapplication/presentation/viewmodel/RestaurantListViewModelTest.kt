package com.eatclubapplication.presentation.viewmodel

import com.eatclubapplication.data.model.Deal
import com.eatclubapplication.data.model.Restaurant
import com.eatclubapplication.data.model.RestaurantList
import com.eatclubapplication.data.repository.RestaurantRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

@ExperimentalCoroutinesApi
class RestaurantListViewModelTest {

    private lateinit var viewModel: RestaurantListViewModel
    private val repository: RestaurantRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRestaurants sets initial state and sorts by best deals`() = runTest {
        val mockRestaurants = listOf(
            Restaurant(
                objectId = "1",
                name = "Restaurant A",
                address1 = "",
                suburb = "",
                cuisines = listOf("Italian"),
                imageLink = "",
                open = "",
                close = "",
                deals = listOf(Deal(objectId = "d1", discount = 10, qtyLeft = 0, dineIn = false))
            ),
            Restaurant(
                objectId = "2",
                name = "Restaurant B",
                address1 = "",
                suburb = "",
                cuisines = listOf("Mexican"),
                imageLink = "",
                open = "",
                close = "",
                deals = listOf(Deal(objectId = "d2", discount = 20, qtyLeft = 0, dineIn = false))
            )
        )
        coEvery { repository.getRestaurants() } returns RestaurantList(restaurants = mockRestaurants)

        viewModel = RestaurantListViewModel(repository)

        advanceUntilIdle()

        assertFalse(viewModel.viewState.value.isLoading)
        assertEquals(2, viewModel.viewState.value.restaurants.size)
        assertEquals("Restaurant B", viewModel.viewState.value.restaurants[0].name) // Sorted by best deals
        assertEquals(SortOption.BEST_DEALS, viewModel.viewState.value.sortOption)
    }

    @Test
    fun `onSearchQueryChanged filters restaurants by name`() = runTest {
        val mockRestaurants = listOf(
            Restaurant(
                objectId = "1",
                name = "Restaurant A",
                address1 = "",
                suburb = "",
                cuisines = listOf("Italian"),
                imageLink = "",
                open = "",
                close = "",
                deals = emptyList()
            ),
            Restaurant(
                objectId = "2",
                name = "Restaurant B",
                address1 = "",
                suburb = "",
                cuisines = listOf("Mexican"),
                imageLink = "",
                open = "",
                close = "",
                deals = emptyList()
            )
        )
        coEvery { repository.getRestaurants() } returns RestaurantList(restaurants = mockRestaurants)

        viewModel = RestaurantListViewModel(repository)
        advanceUntilIdle()

        viewModel.onSearchQueryChanged("t A")
        assertEquals(1, viewModel.viewState.value.filteredRestaurants.size)
        assertEquals("Restaurant A", viewModel.viewState.value.filteredRestaurants[0].name)
    }

    @Test
    fun `onSearchQueryChanged filters restaurants by cuisine`() = runTest {
        val mockRestaurants = listOf(
            Restaurant(
                objectId = "1",
                name = "Restaurant A",
                address1 = "",
                suburb = "",
                cuisines = listOf("Italian"),
                imageLink = "",
                open = "",
                close = "",
                deals = emptyList()
            ),
            Restaurant(
                objectId = "2",
                name = "Restaurant B",
                address1 = "",
                suburb = "",
                cuisines = listOf("Mexican"),
                imageLink = "",
                open = "",
                close = "",
                deals = emptyList()
            )
        )
        coEvery { repository.getRestaurants() } returns RestaurantList(restaurants = mockRestaurants)

        viewModel = RestaurantListViewModel(repository)
        advanceUntilIdle()

        viewModel.onSearchQueryChanged("Mex")
        assertEquals(1, viewModel.viewState.value.filteredRestaurants.size)
        assertEquals("Restaurant B", viewModel.viewState.value.filteredRestaurants[0].name)
    }

    @Test
    fun `sortByName sorts restaurants by name`() = runTest {
        val mockRestaurants = listOf(
            Restaurant(
                objectId = "1",
                name = "Restaurant B",
                address1 = "",
                suburb = "",
                cuisines = listOf("Italian"),
                imageLink = "",
                open = "",
                close = "",
                deals = emptyList()
            ),
            Restaurant(
                objectId = "2",
                name = "Restaurant A",
                address1 = "",
                suburb = "",
                cuisines = listOf("Mexican"),
                imageLink = "",
                open = "",
                close = "",
                deals = emptyList()
            )
        )
        coEvery { repository.getRestaurants() } returns RestaurantList(restaurants = mockRestaurants)

        viewModel = RestaurantListViewModel(repository)
        advanceUntilIdle()

        viewModel.sortByName()
        assertEquals("Restaurant A", viewModel.viewState.value.restaurants[0].name)
        assertEquals("Restaurant B", viewModel.viewState.value.restaurants[1].name)
        assertEquals(SortOption.NAME, viewModel.viewState.value.sortOption)
    }

    @Test
    fun `sortByBestDeals sorts restaurants by highest discount`() = runTest {
        val mockRestaurants = listOf(
            Restaurant(
                objectId = "1",
                name = "Restaurant A",
                address1 = "",
                suburb = "",
                cuisines = listOf("Italian"),
                imageLink = "",
                open = "",
                close = "",
                deals = listOf(Deal(objectId = "d1", discount = 10, qtyLeft = 0, dineIn = false))
            ),
            Restaurant(
                objectId = "2",
                name = "Restaurant B",
                address1 = "",
                suburb = "",
                cuisines = listOf("Mexican"),
                imageLink = "",
                open = "",
                close = "",
                deals = listOf(Deal(objectId = "d2", discount = 30, qtyLeft = 0, dineIn = false))
            ),
            Restaurant(
                objectId = "3",
                name = "Restaurant C",
                address1 = "",
                suburb = "",
                cuisines = listOf("Indian"),
                imageLink = "",
                open = "",
                close = "",
                deals = listOf(Deal(objectId = "d3", discount = 20, qtyLeft = 0, dineIn = false))
            )
        )
        coEvery { repository.getRestaurants() } returns RestaurantList(restaurants = mockRestaurants)

        viewModel = RestaurantListViewModel(repository)
        advanceUntilIdle()

        viewModel.sortByBestDeals()
        assertEquals("Restaurant B", viewModel.viewState.value.restaurants[0].name)
        assertEquals("Restaurant C", viewModel.viewState.value.restaurants[1].name)
        assertEquals("Restaurant A", viewModel.viewState.value.restaurants[2].name)
        assertEquals(SortOption.BEST_DEALS, viewModel.viewState.value.sortOption)
    }

    @Test
    fun `onRestaurantSelected sets detail view state`() = runTest {
        val mockRestaurant = Restaurant(
            objectId = "1",
            name = "Restaurant A",
            address1 = "",
            suburb = "",
            cuisines = listOf("Italian"),
            imageLink = "",
            open = "",
            close = "",
            deals = listOf(
                Deal(objectId = "d1", discount = 10, qtyLeft = 0, dineIn = false),
                Deal(objectId = "d2", discount = 20, qtyLeft = 0, dineIn = false)
            )
        )
        coEvery { repository.getRestaurants() } returns RestaurantList(restaurants = listOf(mockRestaurant))

        viewModel = RestaurantListViewModel(repository)
        advanceUntilIdle()

        viewModel.onRestaurantSelected(mockRestaurant)
        assertEquals(mockRestaurant, viewModel.detailViewState.value.restaurant)
        assertEquals(2, viewModel.detailViewState.value.deals.size)
        assertEquals(20, viewModel.detailViewState.value.deals[0].discount) // Deals sorted by discount
    }

    @Test
    fun `onSettingsClicked toggles showBottomSheet`() = runTest {
        coEvery { repository.getRestaurants() } returns RestaurantList(restaurants = emptyList())
        viewModel = RestaurantListViewModel(repository)
        advanceUntilIdle()

        assertFalse(viewModel.viewState.value.showBottomSheet)
        viewModel.onSettingsClicked(true)
        assertTrue(viewModel.viewState.value.showBottomSheet)
        viewModel.onSettingsClicked(false)
        assertFalse(viewModel.viewState.value.showBottomSheet)
    }
}
