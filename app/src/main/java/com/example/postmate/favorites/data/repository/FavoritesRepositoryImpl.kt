package com.example.postmate.favorites.data.repository

import com.example.postmate.database.PostDao
import com.example.postmate.favorites.domain.repository.FavoritesRepository
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(private val postsDao: PostDao) : FavoritesRepository
{
    override fun getFavorites(): Flow<List<FavoriteModelClass>> {
        return postsDao.getFavorites()
    }

    override suspend fun getFavoritesById(id: Int): FavoriteModelClass {
        return postsDao.getFavoritesById(id)
    }

    override suspend fun insertFavorite(post: FavoriteModelClass) {
        return postsDao.insertFavorite(post)
    }

    override suspend fun deleteFavorite(post: FavoriteModelClass) {
        return postsDao.deleteFavorite(post)
    }

}