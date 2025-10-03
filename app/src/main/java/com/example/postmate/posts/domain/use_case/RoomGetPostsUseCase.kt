package com.example.postmate.posts.domain.use_case

import com.example.postmate.common.Resource
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import com.example.postmate.favorites.domain.repository.FavoritesRepository
import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.domain.repository.RoomPostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class RoomGetPostsUseCase(private val roomPostsRepository: RoomPostsRepository)
{
    operator fun invoke(): Flow<Resource<List<PostModelClass>>> {
        return roomPostsRepository.getPosts()
            .map { list -> Resource.Success(list) as Resource<List<PostModelClass>> }
            .onStart { emit(Resource.Loading()) }
            .catch { e -> emit(Resource.Error(e.message ?: "Failed to get posts")) }
    }

}