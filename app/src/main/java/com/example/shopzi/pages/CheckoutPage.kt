package com.example.shopzi.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopzi.model.ProductModel
import com.example.shopzi.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CheckoutPage(modifier: Modifier = Modifier) {
    val userModel = remember {
        mutableStateOf(UserModel())
    }
    val pfee = remember {
        mutableStateOf(99f)
    }
    val dfee = remember {
        mutableStateOf(49f)
    }
    val pdtList = remember {
        mutableStateListOf<ProductModel>()
    }

    val stotal =remember {
        mutableStateOf(0f)
    }
    val total = remember {
        mutableStateOf(0f)
    }

    fun calc(){
        stotal.value = 0f
        total.value =0f;
        pdtList.forEach {
            if(it.actualPrice.isNotEmpty()){
                val qty = userModel.value.cart[it.id]?:0
                val price = it.actualPrice.replace(",", "").toFloatOrNull() ?: 0f
                stotal.value += price * qty


            }
        }

        total.value = stotal.value + dfee.value + pfee.value
    }

    LaunchedEffect(key1 =Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if(it.isSuccessful){
                    val result = it.result.toObject(UserModel::class.java)
                    if(result!=null){
                        userModel.value = result


                        Firebase.firestore.collection("data")
                            .document("stock").collection("products")
                            .whereIn("id",userModel.value.cart.keys.toList())
                            .get().addOnCompleteListener {
                                task->
                                if(task.isSuccessful){
                                    val result1 = task.result.toObjects(ProductModel::class.java)
                                    pdtList.clear()
                                    pdtList.addAll(result1)
                                    calc()
                                }
                            }
                    }
                }
            }
    }



    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp))

    {
        Text(
            text = "Checkout",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold

        )

        Spacer(modifier= Modifier.height(16.dp))

        Text(text = "Deliver to: ", fontWeight = FontWeight.SemiBold)
        Text(text = userModel.value.name)
        Text(text = userModel.value.address)
        Spacer(modifier= Modifier.height(16.dp))

        HorizontalDivider()
        Spacer(modifier= Modifier.height(25.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            Text(text = "Subtotal", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "₹"+stotal.value.toString(), fontSize = 18.sp)
        }
        Spacer(modifier= Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            Text(text = "Platform Fee(+)", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "₹"+pfee.value.toString(), fontSize = 18.sp)
        }
        Spacer(modifier= Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            Text(text = "Delivery Fee(+)", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "₹"+dfee.value.toString(), fontSize = 18.sp)
        }
        Spacer(modifier= Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier= Modifier.height(16.dp))
        Text(modifier = Modifier.fillMaxWidth(),
            text = "To Pay",
            textAlign = TextAlign.Center
        )

        Text(modifier = Modifier.fillMaxWidth(),
            text = "₹"+total.value.toString(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )


    }

}