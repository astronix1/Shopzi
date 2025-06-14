package com.example.shopzi.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shopzi.pages.CartPage
import com.example.shopzi.pages.FavoritePage
import com.example.shopzi.pages.HomePage
import com.example.shopzi.pages.ProfilePage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val navitemlist = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Favorite", Icons. Default.Favorite),
        NavItem("Cart", Icons.Default.ShoppingCart),
        NavItem("Profile", Icons.Default.Person)
    )
    var si by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar{
                navitemlist.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = si == index,
                        onClick = {
                            si = index
                        },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                        label = { Text(text = item.label)})
                }
            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it),si)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, si: Int) {
    when(si){
        0-> HomePage(modifier)
        1-> FavoritePage(modifier)
        2-> CartPage(modifier)
        3-> ProfilePage(modifier)
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector
)

@Preview
@Composable
fun Pre5(modifier: Modifier = Modifier) {
    HomeScreen(modifier = modifier, navController = NavController(LocalContext.current))
}