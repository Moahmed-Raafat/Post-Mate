package com.example.postmate.favorites.domain.repository

import com.example.postmate.favorites.data.remote.FavoriteModelClass
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavorites(): Flow<List<FavoriteModelClass>>

    suspend fun getFavoritesById(id : Int):FavoriteModelClass

    suspend fun insertFavorite(post : FavoriteModelClass)

    suspend fun deleteFavorite(post : FavoriteModelClass)
}