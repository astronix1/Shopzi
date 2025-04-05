package com.example.shopzi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shopzi.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class AuthViewModel: ViewModel(){
    private val auth = Firebase.auth
    private val firestore =  Firebase.firestore

    fun signup(email: String, name: String,password: String, onComplete: (Boolean, String?)->Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = it.result.user?.uid
                    val userModel = UserModel(name, email, userId!!)
                    firestore.collection("users").document(userId).set(userModel)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                onComplete(true, null)
                            }else{
                                onComplete(false, it.exception?.localizedMessage)
                            }
                        }


                }else{
                    onComplete(false, it.exception?.localizedMessage)
                }
            }

    }
}