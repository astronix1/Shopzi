package com.example.shopzi.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopzi.GlobalNavigation
import com.example.shopzi.components.BannerView
import com.example.shopzi.components.CategoriesView
import com.example.shopzi.components.HeaderView
import com.example.shopzi.components.ProductItemView
import com.example.shopzi.model.ProductModel
import com.example.shopzi.viewmodel.HomeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun HomePage(modifier: Modifier = Modifier, homeViewModel: HomeViewModel = viewModel()) {
    var searchQuery = remember { mutableStateOf("") }

    var recentProducts by remember { mutableStateOf<List<ProductModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("stock")
            .collection("products")
            .limit(10)
            .get()
            .addOnSuccessListener { snapshot ->
                recentProducts = snapshot.documents.mapNotNull { it.toObject(ProductModel::class.java) }
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 32.dp, bottom = 16.dp)
    ) {
        item {
            HeaderView(
                modifier = Modifier,
                searchQuery = searchQuery,
                onSearchClick = {
                    if (searchQuery.value.isNotBlank()) {
                        GlobalNavigation.navController.navigate("searchResult/${searchQuery.value}")
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            BannerView(modifier = Modifier.height(220.dp), homeViewModel = homeViewModel)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Text(
                text = "Categories",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(12.dp))
            CategoriesView(modifier, homeViewModel = homeViewModel)
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Text(
                text = "Just For You",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(recentProducts.chunked(2)) { rowItems ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowItems.forEach { item ->
                    ProductItemView(p = item, modifier = Modifier.weight(1f))
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}