package com.example.postmate.details_and_comments.domain.use_case

import com.example.postmate.details_and_comments.data.remote.CommentModelClass
import com.example.postmate.details_and_comments.data.repository.FakeCommentsRepositoryImpl
import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.data.repository.FakePostsRepositoryImpl
import com.example.postmate.posts.data.repository.FakeRoomPostsRepositoryImpl
import com.example.postmate.posts.domain.use_case.PostsUseCase
import com.example.postmate.posts.domain.use_case.RoomGetPostsUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.collections.forEach


class CommentsUseCaseTest {

    //posts from api
    private lateinit var fakeCommentsRepositoryImpl: FakeCommentsRepositoryImpl
    private lateinit var commentsUseCase: CommentsUseCase

    private lateinit var comments: MutableList<CommentModelClass>

    @Before
    fun setUp()
    {
        fakeCommentsRepositoryImpl= FakeCommentsRepositoryImpl()
        commentsUseCase= CommentsUseCase(fakeCommentsRepositoryImpl)


        comments= mutableListOf<CommentModelClass>()
        comments.add(CommentModelClass(1000,10001,"name 1","email 1","body 1"))
        comments.add(CommentModelClass(1000,10002,"name 2","email 2","body 2"))
        comments.add(CommentModelClass(1000,10003,"name 3","email 3","body 3"))
        comments.add(CommentModelClass(1000,10004,"name 4","email 4","body 4"))
        comments.add(CommentModelClass(1000,10005,"name 5","email 5","body 5"))


        runTest {
            comments.forEach{
                fakeCommentsRepositoryImpl.addComment(it)
            }
        }
    }

    @Test
    fun `make sure the comments get displayed`() = runTest {

        val result = commentsUseCase(1000).drop(1).first()
        val displayedComments = result.data ?: emptyList()

        // posts from api get displayed
        assertThat(displayedComments).isEqualTo(comments)
    }
}