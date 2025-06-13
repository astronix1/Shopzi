package com.example.shopzi.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.Disposable
import com.example.shopzi.AppUtil
import com.example.shopzi.GlobalNavigation
import com.example.shopzi.components.CartItemView
import com.example.shopzi.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

@Composable
fun CartPage(modifier: Modifier = Modifier) {

    val userModel = remember {
        mutableStateOf(UserModel())
    }

    DisposableEffect(Unit) {
        val uid = Firebase.auth.currentUser?.uid
        var listener: ListenerRegistration? = null

        if (uid != null) {
            listener = Firebase.firestore.collection("users")
                .document(uid)
                .addSnapshotListener { snapshot, error ->
                    if (snapshot != null && snapshot.exists()) {
                        val result = snapshot.toObject(UserModel::class.java)
                        if (result != null) {
                            userModel.value = result
                        }
                    }
                }
        }

        // Cleanup
        onDispose {
            listener?.remove()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
            .padding(12.dp)
    ){
        Text(
            text = "Your Cart",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(items = userModel.value.cart.toList(), key = { it.first }) { (productName, quantity) ->
                CartItemView(
                    pId = productName,
                    quantity = quantity,
                )
            }
        }


        Spacer(modifier=Modifier.height(12.dp));


        Button(onClick = {
            GlobalNavigation.navController.navigate("checkout")
        },
            modifier = Modifier.fillMaxWidth().height(51.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E1E1E)
                ,
                contentColor = Color.White
            )

        ){
            Text(text = "Checkout",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
            )
        }






    }
}