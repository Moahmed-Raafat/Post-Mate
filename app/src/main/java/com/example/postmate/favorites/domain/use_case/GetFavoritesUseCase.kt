package com.example.postmate.favorites.domain.use_case

import com.example.postmate.common.Resource
import com.example.postmate.favorites.domain.repository.FavoritesRepository
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GetFavoritesUseCase(private val favoritesRepository: FavoritesRepository)
{
    operator fun invoke(): Flow<Resource<List<FavoriteModelClass>>> {
        return favoritesRepository.getFavorites() // Flow<List<FavoriteModelClass>>
            .map { list -> Resource.Success(list) as Resource<List<FavoriteModelClass>> }
            .onStart { emit(Resource.Loading()) }
            .catch { e -> emit(Resource.Error(e.message ?: "Failed to get favorites")) }
    }

}