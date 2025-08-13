package com.example.shopzi.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopzi.AppUtil
import com.example.shopzi.model.ProductModel
import com.example.shopzi.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun BuyNowPage(modifier: Modifier = Modifier, productId: String)
{


    val context = LocalContext.current
    val userModel = remember { mutableStateOf(UserModel()) }
    val pfee = remember { mutableStateOf(99f) }
    val dfee = remember { mutableStateOf(49f) }
    val pdtList = remember { mutableStateListOf<ProductModel>() }

    val stotal = remember { mutableStateOf(0f) }
    val total = remember { mutableStateOf(0f) }

    fun calc() {
        stotal.value = 0f
        total.value = 0f
        pdtList.forEach {
            if (it.actualPrice.isNotEmpty()) {
                val price = it.actualPrice.replace(",", "").toFloatOrNull() ?: 0f
                stotal.value += price // Single product, qty = 1
            }
        }
        total.value = stotal.value + dfee.value + pfee.value
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get()
            .addOnCompleteListener { userTask ->
                if (userTask.isSuccessful) {
                    val result = userTask.result.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result

                        Firebase.firestore.collection("data")
                            .document("stock")
                            .collection("products")
                            .whereEqualTo("id", productId)
                            .get()
                            .addOnCompleteListener { productTask ->
                                if (productTask.isSuccessful) {
                                    val result1 = productTask.result.toObjects(ProductModel::class.java)
                                    pdtList.clear()
                                    pdtList.addAll(result1)
                                    calc()
                                }
                            }
                    }
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Checkout",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Deliver to: ", fontWeight = FontWeight.SemiBold)
        Text(text = userModel.value.name)
        Text(text = userModel.value.address)

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Subtotal", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "₹${stotal.value}", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Platform Fee(+)", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "₹${pfee.value}", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Delivery Fee(+)", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "₹${dfee.value}", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "To Pay",
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "₹${total.value}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {

                if (userModel.value.address.isNullOrBlank()) {
                    android.widget.Toast.makeText(
                        context,
                        "Please add your address before proceeding.",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                } else {
                    AppUtil.buyNowProductId = productId
                    AppUtil.isBuyNow = true
                    AppUtil.startPayment(total.value)
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(51.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E1E1E),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Pay Now",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
