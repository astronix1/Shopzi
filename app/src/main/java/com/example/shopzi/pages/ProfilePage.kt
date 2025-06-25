package com.example.shopzi.pages

import com.example.shopzi.R
import android.graphics.fonts.Font
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopzi.AppUtil
import com.example.shopzi.GlobalNavigation
import com.example.shopzi.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {

    val userModel = remember{
        mutableStateOf(UserModel())
    }
    var addressInt by remember {
        mutableStateOf(userModel.value.address)
    }
    var context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if(it.isSuccessful){
                    val result =it.result.toObject(UserModel::class.java)
                    if(result!=null) {
                        userModel.value = result
                        addressInt = userModel.value.address
                    }
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Text(text = "Your Profile",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(painter = painterResource(R.drawable.pfc), contentDescription = "pfc",
            modifier = Modifier.height(170.dp). fillMaxWidth()

            )
        Spacer(modifier = Modifier.height(14.dp))
        Text(text = userModel.value.name,
            style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier= Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Address: ",
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Medium)

        )
        Spacer(modifier = Modifier.height(13.dp))
        TextField(
            value = addressInt,
            onValueChange = {
                addressInt = it
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if(addressInt.isNotEmpty()){
                    Firebase.firestore.collection("users")
                        .document(FirebaseAuth.getInstance().currentUser?.uid!!)
                        .update("address", addressInt)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                AppUtil.showToast(context, "Address Updated!")
                            }
                        }
                }
                else{
                    AppUtil.showToast(context, "Address cannot be empty!")
                }

            })

        )


        Spacer(modifier = Modifier.height(17.dp))

        Text(
            text = "Email: ",
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Medium)

        )
        Spacer(modifier = Modifier.height(13.dp))
        Text(text = userModel.value.email,
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Medium),
            )

        Spacer(modifier = Modifier.height(17.dp))
        Button(
            onClick = {
                GlobalNavigation.navController.navigate("orders")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E88E5),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "View My Orders",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(17.dp))
        TextButton(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                GlobalNavigation.navController.popBackStack()
                GlobalNavigation.navController.navigate("auth")
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Logout",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium))

        }


    }

}