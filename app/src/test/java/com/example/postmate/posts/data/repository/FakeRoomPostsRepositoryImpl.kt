package com.example.postmate.posts.data.repository

import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.domain.repository.RoomPostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRoomPostsRepositoryImpl: RoomPostsRepository
{
    private val posts = mutableListOf<PostModelClass>()
    private val postsFlow = MutableStateFlow<List<PostModelClass>>(emptyList())

    override fun getPosts(): Flow<List<PostModelClass>> = postsFlow

    override suspend fun insertPostsList(posts: List<PostModelClass>) {
        this.posts.clear()
        this.posts.addAll(posts)
        postsFlow.value = this.posts.toList()
    }

    fun clearPosts() {
        posts.clear()
        postsFlow.value = emptyList()
    }
}