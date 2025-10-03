package com.example.postmate.details_and_comments.domain.repository

import com.example.postmate.details_and_comments.data.remote.CommentModelClass

interface CommentsRepository {
    suspend fun getComments(postId: Int) : List<CommentModelClass>
}