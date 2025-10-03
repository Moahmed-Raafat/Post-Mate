package com.example.postmate.posts.domain.use_case

import com.example.postmate.common.Resource
import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PostsUseCase(private val postsRepository: PostsRepository) {
    operator fun invoke(): Flow<Resource<List<PostModelClass>>> = flow {
        try {
            emit(Resource.Loading())
            val posts:List<PostModelClass> = postsRepository.postsList()
            emit(Resource.Success(posts))
        }
        catch ( e : HttpException)
        {
            emit(Resource.Error("an unexpected error occurred"))
        }
        catch (e : IOException)
        {
            emit(Resource.Error("could not reach the server . check your internet connection"))
        }

    }
}