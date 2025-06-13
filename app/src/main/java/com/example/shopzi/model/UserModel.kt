package com.example.shopzi.model

data class UserModel(
    val name: String="",
    val email: String="",
    val uid: String="",
    val cart: Map<String, Long> = emptyMap(),
    val address: String="",
)
