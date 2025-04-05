package com.example.shopzi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopzi.screen.AuthScreen
import com.example.shopzi.screen.HomeScreen
import com.example.shopzi.screen.LoginScreen
import com.example.shopzi.screen.SignupScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val isLoggedIn = Firebase.auth.currentUser != null
    val std = if(isLoggedIn) "homescreen" else "auth"
    NavHost(navController = navController, startDestination = std) {
        composable("auth") {
            AuthScreen( navController)
        }
        composable("login"){
            LoginScreen(modifier, navController)
        }
        composable("signup"){
            SignupScreen(modifier, navController)
        }
        composable("homescreen"){
            HomeScreen(modifier, navController)
        }
    }
}