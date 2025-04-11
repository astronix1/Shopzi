package com.example.shopzi.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shopzi.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType
import java.time.format.TextStyle

@Composable
fun ProductDetailsPage(modifier: Modifier = Modifier, productId: String) {
    var product by remember {
        mutableStateOf(ProductModel())
    }
    val pagerState = rememberPagerState(0) {
        product.images.size
    }
    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data").document("stock")
            .collection("products")
            .document(productId).get().addOnCompleteListener {
                if(it.isSuccessful){
                    var result = it.result.toObject(ProductModel::class.java)
                    if(result!=null){
                        product = result
                    }
                }
            }
    }


    Column(
        modifier= modifier.fillMaxSize().padding(17.dp)
            .verticalScroll(rememberScrollState())
    )
    {
        Text(text = product.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
            )

        Spacer(modifier = Modifier.height(8.dp))

        Column {

            HorizontalPager(state = pagerState, pageSpacing = 20.dp)  {
                AsyncImage(model = product.images[it],
                    contentDescription = "imagess",
                    modifier = Modifier
                        .height(220.dp)
                    .fillMaxWidth().clip(RoundedCornerShape(15.dp))
                )
            }
            Spacer(modifier =  Modifier.height(10.dp))

            DotsIndicator(
                dotCount = product.images.size,
                type = ShiftIndicatorType(dotsGraphic = DotGraphic(color = MaterialTheme.colorScheme.primary,
                    size = 6.dp)),
                pagerState = pagerState
            )



        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Column {
                if (product.price != product.actualPrice) {
                    Text(
                        text = "₹${product.price}",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough)
                    )
                }
                Text(
                    text = "₹${product.actualPrice}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { /* TODO: Add to cart action */ },
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        Color(0xFFFFEBEE),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Cart",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {

        },
            modifier = Modifier.fillMaxWidth().height(51.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E1E1E)
                ,
                contentColor = Color.White
            )

        ){
            Text(text = "Add to cart",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                )
        }
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {

        },
            modifier = Modifier.fillMaxWidth().height(51.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF5722)
            )

        ){
            Text(text = "Buy Now",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
            )
        }


        Spacer(modifier = Modifier.height(25.dp))

        Text(text = product.description, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)



    }


}