package com.example.postmate.favorites.domain.use_case

import com.example.postmate.common.Resource
import com.example.postmate.favorites.domain.repository.FavoritesRepository
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavoriteByIdUseCase(private val favoritesRepository: FavoritesRepository)
{

    operator fun invoke(id: Int): Flow<Resource<FavoriteModelClass>> = flow {

        try {
            emit(Resource.Loading())
            val favoritesModelClass = favoritesRepository.getFavoritesById(id)
            emit(Resource.Success(favoritesModelClass))
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to get favorites"))
        }
    }

}