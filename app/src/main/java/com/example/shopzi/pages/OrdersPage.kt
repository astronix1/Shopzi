package com.example.shopzi.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background

import coil.compose.AsyncImage
import com.example.shopzi.model.OrderModel
import com.example.shopzi.model.ProductModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrdersPage(modifier: Modifier = Modifier) {
    val orders = remember { mutableStateListOf<OrderModel>() }
    val productCache = remember { mutableStateMapOf<String, ProductModel>() }
    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    // Fetch orders
    LaunchedEffect(Unit) {
        val uid = auth.currentUser?.uid ?: return@LaunchedEffect

        val snapshot = db.collection("orders")
            .whereEqualTo("userId", uid)
            .get()
            .await()

        orders.clear()
        orders.addAll(snapshot.toObjects(OrderModel::class.java))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "My Orders", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(16.dp))

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No orders found")
            }
        } else {
            LazyColumn {
                items(orders) { order ->
                    OrderCard(order, productCache)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderCard(order: OrderModel, productCache: MutableMap<String, ProductModel>) {
    val db = Firebase.firestore

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Order header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Order ID: ${order.id}", fontSize = 16.sp)
                Text(text = formatDate(order.date), fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Status badge
            val statusColor = when (order.status.lowercase()) {
                "delivered" -> MaterialTheme.colorScheme.primary
                "pending" -> MaterialTheme.colorScheme.secondary
                "cancelled" -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.tertiary
            }
            Text(
                text = order.status.uppercase(),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .background(
                        color = statusColor,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                fontSize = 12.sp
            )

            Text(text = "Address: ${order.address}", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Items:", fontSize = 14.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)

            // Items list
            var total = 0.0
            order.items.forEach { (productId, qty) ->
                val product = productCache[productId]

                if (product == null) {
                    LaunchedEffect(productId) {
                        val doc = db.collection("data")
                            .document("stock")
                            .collection("products")
                            .document(productId)
                            .get()
                            .await()
                            .toObject(ProductModel::class.java)

                        if (doc != null) {
                            productCache[productId] = doc
                        }
                    }
                    Text(text = "- Loading... x$qty", fontSize = 13.sp)
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        // Circular product image
                        if (product.images.isNotEmpty()) {
                            AsyncImage(
                                model = product.images[0],
                                contentDescription = product.title,
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(end = 8.dp)
                                    .clip(MaterialTheme.shapes.small)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = product.title, fontSize = 14.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                            Text(text = "Qty: $qty", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = "Price: ₹${product.actualPrice}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }


        }
    }
}



fun formatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}
