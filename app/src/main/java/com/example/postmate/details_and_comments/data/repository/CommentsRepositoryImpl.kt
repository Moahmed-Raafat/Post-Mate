package com.example.postmate.details_and_comments.data.repository

import com.example.postmate.details_and_comments.data.remote.CommentModelClass
import com.example.postmate.details_and_comments.domain.repository.CommentsRepository
import com.example.postmate.common.ServiceAPI


class CommentsRepositoryImpl(private val serviceAPI: ServiceAPI): CommentsRepository
{
    override suspend fun getComments(postId: Int): List<CommentModelClass> {
        return serviceAPI.getComments(postId)
    }

}