package com.eatclubapplication.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eatclubapplication.data.model.Deal

@Composable
fun DealCard(
    deal: Deal,
    modifier: Modifier = Modifier,
    onRedeemClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side - Deal info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Discount with lightning icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Lightning deal",
                        tint = Color(0xFFFF8C00), // Orange color
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${deal.discount}% Off",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Time range
                deal.open?.let { open ->
                    deal.close?.let { close ->
                        Text(
                            text = "Between $open - $close",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Quantity left
                Text(
                    text = "${deal.qtyLeft} Deals Left",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Right side - Redeem button
            OutlinedButton(
                onClick = onRedeemClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFE53E3E)
                ),
                border = BorderStroke(1.dp, Color(0xFFE53E3E)),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "Redeem",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

class SampleDealProvider : PreviewParameterProvider<Deal> {
    override val values = sequenceOf(
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
}

@Preview
@Composable
fun PreviewDealCard(@PreviewParameter(SampleDealProvider::class) deal: Deal) {
    DealCard(deal = deal)
}

