package com.example.shopzi.components

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.shopzi.AppUtil
import com.example.shopzi.GlobalNavigation
import com.example.shopzi.model.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun CartItemView(
    modifier: Modifier = Modifier,
    pId: String,
    quantity: Long
) {
    val context = LocalContext.current
    var product by remember { mutableStateOf(ProductModel()) }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data").document("stock")
            .collection("products").document(pId).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(ProductModel::class.java)
                    if (result != null) {
                        product = result
                    }
                }
            }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp).clickable{
                GlobalNavigation.navController.navigate("productdetailspage/${pId}")
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            if (product.images.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(product.images[0]),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Product Info
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "₹${product.actualPrice}", style = MaterialTheme.typography.bodyLarge)
            }

            // Quantity UI (no logic)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { AppUtil.removefc(context, productId = pId) },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(32.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("-", style = MaterialTheme.typography.bodyLarge)
                }

                Text(text = quantity.toString(), style = MaterialTheme.typography.bodyLarge)

                Button(
                    onClick = { AppUtil.addtocart(context, productId = pId) },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(32.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("+", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
