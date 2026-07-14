package com.example.shopzi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shopzi.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories: StateFlow<List<CategoryModel>> = _categories.asStateFlow()

    private val _isLoadingCategories = MutableStateFlow(true)
    val isLoadingCategories: StateFlow<Boolean> = _isLoadingCategories.asStateFlow()

    private val _banners = MutableStateFlow<List<String>>(emptyList())
    val banners: StateFlow<List<String>> = _banners.asStateFlow()

    init {
        fetchCategoriesWithListener()
        fetchBannersWithListener()
    }

    private fun fetchCategoriesWithListener() {
        firestore.collection("data").document("stock")
            .collection("categories")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _isLoadingCategories.value = false
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val resultList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(CategoryModel::class.java)
                    }
                    _categories.value = resultList
                }
                _isLoadingCategories.value = false
            }
    }

    private fun fetchBannersWithListener() {
        firestore.collection("data").document("banners")
            .addSnapshotListener { snapshot, error ->
                if (error == null && snapshot != null && snapshot.exists()) {
                    _banners.value = snapshot.get("urls") as? List<String> ?: emptyList()
                }
            }
    }
}