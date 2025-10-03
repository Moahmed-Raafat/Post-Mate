package com.example.postmate.favorites.domain.use_case

import com.example.postmate.favorites.domain.repository.FavoritesRepository
import com.example.postmate.favorites.data.remote.FavoriteModelClass

class DeleteFavoriteUseCase(private val favoritesRepository: FavoritesRepository)
{
    suspend operator fun invoke(favoriteModelClass: FavoriteModelClass)
    {
        favoritesRepository.deleteFavorite(favoriteModelClass)
    }
}