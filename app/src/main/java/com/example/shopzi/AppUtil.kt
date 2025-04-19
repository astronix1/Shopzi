package com.example.shopzi

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

object AppUtil {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
}