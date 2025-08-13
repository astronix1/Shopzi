 package com.example.shopzi

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopzi.ui.theme.ShopziTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.firestore.firestore
import com.razorpay.PaymentResultListener

 class MainActivity : ComponentActivity() , PaymentResultListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firestore = Firebase.firestore

        val settings = FirebaseFirestoreSettings.Builder()
            .setLocalCacheSettings(
                PersistentCacheSettings.newBuilder()
                    .build()
            )
            .build()

        firestore.firestoreSettings = settings
        enableEdgeToEdge()
        setContent {
            ShopziTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
     override fun onPaymentSuccess(p0:String?){

         if(AppUtil.isBuyNow){
             AppUtil.addSingleProductOrder(AppUtil.buyNowProductId, this)
             AppUtil.isBuyNow = false
         }
         else{
             AppUtil.clearandadd()
         }

         val builder = AlertDialog.Builder(this)
         builder.setTitle("Payment Successful")
         builder.setMessage("Your order has been placed successfully")
         builder.setPositiveButton("OK") { dialog, which ->
             GlobalNavigation.navController.popBackStack()
             GlobalNavigation.navController.navigate("homescreen")
         }
         builder.setCancelable(false)
         builder.show()
     }

     override fun onPaymentError(p0: Int, p1:String?){
         AppUtil.showToast(this, "Payment failed")
         AppUtil.isBuyNow = false
     }
}



