package com.example.shopzi.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopzi.GlobalNavigation
import com.example.shopzi.components.BannerView
import com.example.shopzi.components.CategoriesView
import com.example.shopzi.components.HeaderView

@Composable
fun HomePage(modifier: Modifier = Modifier) {

    var searchQuery = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    ){
        HeaderView(
            modifier = Modifier,
            searchQuery = searchQuery,
            onSearchClick = {
                if (searchQuery.value.isNotBlank()) {
                    GlobalNavigation.navController.navigate("searchResult/${searchQuery.value}")
                }
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        BannerView(modifier = Modifier.height(220.dp))
        Text(
            text = "Categories",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        CategoriesView(modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun pre7(modifier: Modifier = Modifier) {
    HomePage(modifier)
}