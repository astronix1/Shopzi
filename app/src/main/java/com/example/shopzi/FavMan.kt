package com.example.shopzi

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.shopzi.model.ProductModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "favorites_store")

object FavMan {
    private val FAVORITES_KEY = stringPreferencesKey("favorites_list")
    private val gson = Gson()

    suspend fun addFavorite(context: Context, product: ProductModel) {
        val current = getFavoritesList(context)
        if (current.none { it.id == product.id }) {
            val updated = current.toMutableList()
            updated.add(product)
            saveList(context, updated)
        }
    }

    suspend fun removeFavorite(context: Context, productId: String) {
        val current = getFavoritesList(context)
        val updated = current.filterNot { it.id == productId }
        saveList(context, updated)
    }

    suspend fun saveList(context: Context, list: List<ProductModel>) {
        val json = gson.toJson(list)
        context.dataStore.edit { prefs ->
            prefs[FAVORITES_KEY] = json
        }
    }

    suspend fun getFavoritesList(context: Context): List<ProductModel> {
        val prefs = context.dataStore.data.map { it[FAVORITES_KEY] ?: "[]" }.first()
        return gson.fromJson(prefs, object : TypeToken<List<ProductModel>>() {}.type)
    }

    fun favoritesFlow(context: Context): Flow<List<ProductModel>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[FAVORITES_KEY] ?: "[]"
            gson.fromJson(json, object : TypeToken<List<ProductModel>>() {}.type)
        }
    }
}
