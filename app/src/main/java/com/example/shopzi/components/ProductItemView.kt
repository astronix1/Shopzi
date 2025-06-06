package com.example.shopzi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shopzi.AppUtil
import com.example.shopzi.GlobalNavigation
import com.example.shopzi.model.ProductModel

@Composable
fun ProductItemView(modifier: Modifier = Modifier, p: ProductModel) {
    Card(
        modifier = modifier
            .clickable{
              GlobalNavigation.navController.navigate("productdetailspage/"+ p.id)
            }
            .padding(10.dp)
            .fillMaxWidth()
            .shadow(5.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            var context = LocalContext.current
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)) {

                AsyncImage(
                    model = p.images.firstOrNull(),
                    contentDescription = p.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )

                if (p.price != p.actualPrice) {
                    Text(
                        text = "${getDiscountPercentage(p.price, p.actualPrice)}% OFF",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(Color(0xFFFF7043), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = p.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1,
//
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Column {
                    if (p.price != p.actualPrice) {
                        Text(
                            text = "₹${p.price}",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                    Text(
                        text = "₹${p.actualPrice}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {

                        AppUtil.addtocart(context, p.id)

                    },
                    modifier = Modifier
                        .size(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = Color(0xFF6D4C41),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        }
    }
}

private fun getDiscountPercentage(originalPriceStr: String, discountedPriceStr: String): Int {
    val original = originalPriceStr.replace(",", "").toIntOrNull()
    val discounted = discountedPriceStr.replace(",", "").toIntOrNull()

    return if (original != null && discounted != null && original > discounted) {
        ((original - discounted) * 100 / original)
    } else {
        0
    }
}


