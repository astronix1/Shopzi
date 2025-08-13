package com.example.shopzi.pages

import android.app.appsearch.SearchResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopzi.GlobalNavigation
import com.example.shopzi.components.ProductItemView
import com.example.shopzi.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun SearchResult(searchQuery: String) {
    val productlist = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }
    LaunchedEffect(searchQuery) {
        Firebase.firestore.collection("data")
            .document("stock")
            .collection("products")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val allProducts = task.result.documents.mapNotNull { doc ->
                        doc.toObject(ProductModel::class.java)
                    }
                    // Filter locally since Firestore can't do partial matches easily
                    productlist.value = allProducts.filter { product ->
                        product.title.contains(searchQuery, ignoreCase = true) ||
                                product.description.contains(searchQuery, ignoreCase = true)
                    }
                }
            }
    }


    Column(
        modifier = Modifier.padding(18.dp).fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.height(50.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){

            IconButton( onClick = {
                GlobalNavigation.navController.popBackStack()
            },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }


            Spacer(modifier = Modifier.width(10.dp))



            Text(text = "Results for \"$searchQuery\"",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }


        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize().fillMaxSize()
        ) {
            items(productlist.value.chunked(2)){rowItems->
                Row{
                    rowItems.forEach{item->
                        ProductItemView(p = item, modifier = Modifier.weight(1f))
                    }
                    if(rowItems.size == 1){
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

            }
        }

    }
}