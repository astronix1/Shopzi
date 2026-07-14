package com.example.shopzi.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shopzi.GlobalNavigation
import com.example.shopzi.model.CategoryModel
import com.example.shopzi.viewmodel.HomeViewModel

@Composable
fun CategoriesView(modifier: Modifier = Modifier, homeViewModel: HomeViewModel) {

    val categoryList by homeViewModel.categories.collectAsState()
    val isLoading by homeViewModel.isLoadingCategories.collectAsState()

    if (isLoading && categoryList.isEmpty()) {
        Box(



            modifier = modifier.fillMaxWidth().height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categoryList) { item ->
                CategoryItem(c = item)
            }
        }
    }
}

@Composable
fun CategoryItem(c: CategoryModel) {
    Card(
        modifier = Modifier.size(100.dp).clickable{
            GlobalNavigation.navController.navigate("categoryproductpage/"+ c.id )
        },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = c.imageUrl,
                contentDescription = c.name,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = c.name, textAlign = TextAlign.Center)
        }
    }
}