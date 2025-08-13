package com.example.shopzi.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shopzi.FavMan
import com.example.shopzi.GlobalNavigation
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Row

@Composable
fun FavoritePage(modifier: Modifier = Modifier) {
    val context = GlobalNavigation.navController.context
    val favoritesState = FavMan.favoritesFlow(context).collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Favorites",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (favoritesState.value.isEmpty()) {
            Text(
                text = "No favorites yet",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 16.sp
            )
        } else {
            LazyColumn {
                items(favoritesState.value) { product ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                // Navigate to Product Details page
                                GlobalNavigation.navController.navigate("productdetailspage/${product.id}")
                            }
                    ) {
                        if (product.images.isNotEmpty()) {
                            AsyncImage(
                                model = product.images[0],
                                contentDescription = product.title,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            val maxChars = 30
                            val displayTitle = if (product.title.length > maxChars) {
                                product.title.take(maxChars) + "..."
                            } else {
                                product.title
                            }
                            Text(displayTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp, )
                            Text("Price: ₹${product.actualPrice}", fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            scope.launch {
                                FavMan.removeFavorite(context, product.id)
                            }
                        }) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Remove from favorites",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}
