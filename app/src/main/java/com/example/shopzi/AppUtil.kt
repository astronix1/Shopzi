package com.example.shopzi

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.shopzi.model.OrderModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razorpay.Checkout
import org.json.JSONObject
import java.util.UUID

object AppUtil {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun addtocart(context: Context, productId: String){
        val userdoc = Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
        userdoc.get().addOnCompleteListener {
            if(it.isSuccessful){
                val cart = it.result.get("cart") as? Map<String, Long>?:emptyMap()
                val cq = cart[productId]?:0
                val uc = mapOf("cart.$productId" to cq+1)
                userdoc.update(uc).addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast(context, "Added to cart")
                    }else{
                        showToast(context, "Failed to add to cart")
                    }
                }
            }
        }
    }

    fun removefc(context: Context, productId: String){
        val userdoc = Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
        userdoc.get().addOnCompleteListener {
            if(it.isSuccessful){
                val cart = it.result.get("cart") as? Map<String, Long>?:emptyMap()
                val cq = cart[productId]?:0
                val uc =
                    if(cq-1<=0){
                        mapOf("cart.$productId" to FieldValue.delete())
                    }
                else{
                        mapOf("cart.$productId" to cq-1)
                    }


                userdoc.update(uc).addOnCompleteListener {
                    if(it.isSuccessful){
                        showToast(context, "Removed from cart")
                    }else{
                        showToast(context, "Failed to remove from cart")
                    }
                }
            }
        }
    }

    fun clearandadd(){
        val userdoc = Firebase.firestore.collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
        userdoc.get().addOnCompleteListener {
            if(it.isSuccessful){
                val curCart = it.result.get("cart") as? Map<String, Long>?:emptyMap()
                val order = OrderModel(
                    id = "ORD_"+UUID.randomUUID().toString().replace("-","").take(10).uppercase(),
                    date = com.google.firebase.Timestamp.now(),
                    userId = Firebase.auth.currentUser?.uid!!,
                    items = curCart,
                    status = "pending",
                    address = it.result.get("address") as? String?:""

                )

                Firebase.firestore.collection("orders")
                    .document(order.id).set(order)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            userdoc.update("cart", FieldValue.delete())


                        }
                    }
            }
        }
    }
    fun rzkey(): String{
        return "rzp_test_S5EMDCKV0Mrjlb"
    }

    fun startPayment(amt : Float){
        val  checkout = Checkout()
        checkout.setKeyID(rzkey())


        val options = JSONObject()
        options.put("name", "Shopzi")
        options.put("description", "")
        options.put("amount", amt*100)
        options.put("currency", "INR")

        checkout.open(GlobalNavigation.navController.context as Activity, options)






    }


}