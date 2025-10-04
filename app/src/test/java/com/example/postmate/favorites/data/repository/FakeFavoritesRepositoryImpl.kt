package com.example.postmate.favorites.data.repository

import com.example.postmate.favorites.data.remote.FavoriteModelClass
import com.example.postmate.favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeFavoritesRepositoryImpl: FavoritesRepository {

    private val favorites = mutableListOf<FavoriteModelClass>()
    private val favoritesFlow = MutableStateFlow<List<FavoriteModelClass>>(emptyList())

    override fun getFavorites(): Flow<List<FavoriteModelClass>> = favoritesFlow


    override suspend fun getFavoritesById(id: Int): FavoriteModelClass {
        return favorites.find { it.id == id }
            ?: FavoriteModelClass(0, 0, "empty", "empty") // never throw
    }

    override suspend fun insertFavorite(post: FavoriteModelClass) {
        // avoid duplicates
        if (favorites.none { it.id == post.id }) {
            favorites.add(post)
            favoritesFlow.value = favorites.toList()
        }
    }

    override suspend fun deleteFavorite(post: FavoriteModelClass) {
        favorites.removeAll { it.id == post.id }
        favoritesFlow.value = favorites.toList()
    }

    // helper function for tests to clear all favorites
    fun clearFavorites() {
        favorites.clear()
        favoritesFlow.value = emptyList()
    }
}