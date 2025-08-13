package com.example.shopzi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun HeaderView(
    modifier: Modifier = Modifier,
    searchQuery: MutableState<String>,
    onSearchClick: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnCompleteListener {
                name = it.result.get("name").toString().split(" ").first()
            }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (!isSearching) {
            // Normal mode: Welcome + name + search icon
            Column {
                Text(text = "Welcome to Shopzi!")
                Text(
                    text = name,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
            IconButton(onClick = { isSearching = true }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        } else {
            // Search mode: TextField + close icon
            androidx.compose.material3.OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                placeholder = { Text("Search products...") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = {
                if (searchQuery.value.isNotBlank()) {
                    onSearchClick(searchQuery.value)
                }
                isSearching = false // Hide search bar after search
            }) {
                Icon(Icons.Default.Search, contentDescription = "Submit Search")
            }
        }
    }
}


