package com.example.postmate.details_and_comments.data.repository

import com.example.postmate.details_and_comments.data.remote.CommentModelClass
import com.example.postmate.details_and_comments.domain.repository.CommentsRepository
import com.example.postmate.posts.data.remote.PostModelClass
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FakeCommentsRepositoryImpl: CommentsRepository {

    private val comments = mutableListOf<CommentModelClass>()

    override suspend fun getComments(postId: Int): List<CommentModelClass> {
        return comments
    }

    fun addComment(comment: CommentModelClass)
    {
        comments.add(comment)
    }
}