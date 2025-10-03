package com.example.postmate.details_and_comments.domain.use_case

import com.example.postmate.details_and_comments.data.remote.CommentModelClass
import com.example.postmate.details_and_comments.domain.repository.CommentsRepository
import com.example.postmate.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CommentsUseCase(private val commentsRepository: CommentsRepository) {
    operator fun invoke(postId: Int): Flow<Resource<List<CommentModelClass>>> = flow {
        try {
            emit(Resource.Loading())
            val comments:List<CommentModelClass> = commentsRepository.getComments(postId)
            emit(Resource.Success(comments))
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