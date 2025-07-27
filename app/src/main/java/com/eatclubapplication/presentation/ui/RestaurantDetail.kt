package com.eatclubapplication.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eatclubapplication.data.model.Deal
import com.eatclubapplication.data.model.Restaurant

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RestaurantDetail(
    restaurant: Restaurant,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onLocationClick: () -> Unit = {},
    onFavouriteClick: () -> Unit = {},
    onRedeemClick: (Deal) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Image carousel section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            // Restaurant image
            AsyncImage(
                model = restaurant.imageLink,
                contentDescription = restaurant.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                error = painterResource(id = android.R.drawable.ic_menu_gallery)
            )

            // New badge (if needed - you can add a property to Restaurant model)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(
                        color = Color(0xFFE53E3E),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "New",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Action buttons row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionButton(
                icon = Icons.Default.List,
                text = "Menu",
                onClick = onMenuClick
            )
            ActionButton(
                icon = Icons.Default.Call,
                text = "Call us",
                onClick = onCallClick
            )
            ActionButton(
                icon = Icons.Default.LocationOn,
                text = "Location",
                onClick = onLocationClick
            )
            ActionButton(
                icon = Icons.Default.FavoriteBorder,
                text = "Favourite",
                onClick = onFavouriteClick
            )
        }

        // Restaurant info section
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            // Restaurant name
            Text(
                text = restaurant.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Cuisines and price range
            CuisineText(restaurant.cuisines)

            // Hours
            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Hours",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Hours: ${restaurant.open} - ${restaurant.close}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Location
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onLocationClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                Column {
                    Text(
                        text = "${restaurant.address1}, ${restaurant.suburb}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "1.0km Away", // You might want to add distance to your model
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // Deals section
            if (restaurant.deals.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    restaurant.deals.forEach { deal ->
                        DealCard(
                            deal = deal,
                            onRedeemClick = { onRedeemClick(deal) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CuisineText(cuisines: List<String>) {
    FlowRow(
        modifier = Modifier.padding(top = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        cuisines.forEachIndexed { index, cuisine ->
            Text(
                text = cuisine,
                fontSize = 14.sp,
                color = Color.Gray
            )
            if (index < cuisines.size - 1) {
                Text(
                    text = "•",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


class RestaurantPreviewParameterProvider : PreviewParameterProvider<Restaurant> {
    override val values = sequenceOf(
        // Masala Kitchen - Indian restaurant with lightning deals
        Restaurant(
            objectId = "DEA567C5-F64C-3C03-FF00-E3B24909BE00",
            name = "Masala Kitchen",
            address1 = "55 Walsh Street",
            suburb = "Lower East",
            cuisines = listOf("Indian", "Brazilian", "Breakfast"),
            imageLink = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
            open = "3:00pm",
            close = "9:00pm",
            deals = listOf(
                Deal(
                    objectId = "DEA567C5-0000-3C03-FF00-E3B24909BE00",
                    discount = 50,
                    dineIn = false,
                    lightning = true,
                    open = "3:00pm",
                    close = "9:00pm",
                    qtyLeft = 5
                ),
                Deal(
                    objectId = "DEA567C5-1111-3C03-FF00-E3B24909BE00",
                    discount = 40,
                    dineIn = true,
                    lightning = false,
                    qtyLeft = 4
                )
            )
        ),

        // ABC Chicken - Asian restaurant with many cuisines
        Restaurant(
            objectId = "D80263E8-FD89-2C70-FF6B-D854ADB8DB00",
            name = "ABC Chicken",
            address1 = "361 Queen Street",
            suburb = "Melbourne",
            cuisines = listOf(
                "Asian", "Contemporary", "Fried Chicken", "Korean",
                "Salads", "Ribs", "Seafood", "Soup", "Vegetarian"
            ),
            imageLink = "https://demo.eccdn.com.au/images/D80263E8-FD89-2C70-FF6B-D854ADB8DB00/eatclub_1634706351211.jpg",
            open = "12:00pm",
            close = "11:00pm",
            deals = listOf(
                Deal(
                    objectId = "D80263E8-0000-2C70-FF6B-D854ADB8DB00",
                    discount = 30,
                    dineIn = false,
                    lightning = false,
                    qtyLeft = 1
                ),
                Deal(
                    objectId = "D80263E8-1111-2C70-FF6B-D854ADB8DB00",
                    discount = 20,
                    dineIn = true,
                    lightning = false,
                    qtyLeft = 4
                )
            )
        ),

        // Single restaurant with no deals (edge case)
        Restaurant(
            objectId = "NO-DEALS-RESTAURANT-ID",
            name = "Simple Cafe",
            address1 = "123 Main Street",
            suburb = "City Center",
            cuisines = listOf("Coffee", "Cafe"),
            imageLink = "https://example.com/cafe-image.jpg",
            open = "7:00am",
            close = "4:00pm",
            deals = emptyList()
        ),

        // Restaurant with single cuisine (minimal case)
        Restaurant(
            objectId = "SINGLE-CUISINE-ID",
            name = "Pizza Palace",
            address1 = "789 Food Street",
            suburb = "Downtown",
            cuisines = listOf("Pizza"),
            imageLink = "https://example.com/pizza-image.jpg",
            open = "11:00am",
            close = "10:00pm",
            deals = listOf(
                Deal(
                    objectId = "PIZZA-DEAL-ID",
                    discount = 25,
                    dineIn = true,
                    lightning = true,
                    open = "5:00pm",
                    close = "7:00pm",
                    qtyLeft = 10
                )
            )
        )
    )
}


@Preview(showBackground = true)
@Composable
fun RestaurantDetailPreview(
    @PreviewParameter(RestaurantPreviewParameterProvider::class) restaurant: Restaurant
) {
    RestaurantDetail(
        restaurant = restaurant,
        onMenuClick = {},
        onCallClick = {},
        onLocationClick = {},
        onFavouriteClick = {},
        onRedeemClick = {}
    )
}

@Preview(showBackground = true, name = "Masala Kitchen")
@Composable
fun MasalaKitchenPreview() {
    val restaurant = RestaurantPreviewParameterProvider().values.first()
    RestaurantDetail(restaurant = restaurant)
}

@Preview(showBackground = true, name = "ABC Chicken - Many Cuisines")
@Composable
fun ABCChickenPreview() {
    val restaurant = RestaurantPreviewParameterProvider().values.elementAt(1)
    RestaurantDetail(restaurant = restaurant)
}


