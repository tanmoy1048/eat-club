package com.eatclubapplication.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.eatclubapplication.data.model.Restaurant

@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box {
                // Restaurant Image
                AsyncImage(
                    model = restaurant.imageLink,
                    contentDescription = restaurant.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(200.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                    error = painterResource(id = android.R.drawable.ic_menu_gallery)
                )

                // Deal Banner (if restaurant has deals)
                restaurant.deals.firstOrNull()?.let { deal ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                            .align(Alignment.TopStart)
                            .background(
                                color = Color(0xFFE53E3E),
                                shape = RoundedCornerShape(
                                    size = 4.dp
                                )
                            )
                            .padding(horizontal = 12.dp, vertical = 2.dp)

                    ) {
                        Text(
                            text = "${deal.discount}% off",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(all = 12.dp)) {
                // Restaurant Name
                Text(
                    text = restaurant.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Distance and Location
                Text(
                    text = "${restaurant.address1}, ${restaurant.suburb}",
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )

                // Cuisine and Service Options
                Text(
                    text = restaurant.cuisines.joinToString(", "),
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

