package com.example.shopzi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopzi.pages.CategoryProductPage
import com.example.shopzi.pages.ProductDetailsPage
import com.example.shopzi.screen.AuthScreen
import com.example.shopzi.screen.HomeScreen
import com.example.shopzi.screen.LoginScreen
import com.example.shopzi.screen.SignupScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    GlobalNavigation.navController = navController
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
        composable("categoryproductpage/{categoryId}"){
            var categoryId = it.arguments?.getString("categoryId")
            CategoryProductPage(modifier, categoryId!!)
        }
        composable("productdetailspage/{productId}"){
            var productId = it.arguments?.getString("productId")
            ProductDetailsPage(modifier, productId!!)
        }
    }
}

object GlobalNavigation{
    lateinit var navController: NavHostController
}