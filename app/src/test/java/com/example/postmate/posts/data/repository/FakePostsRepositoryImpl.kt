package com.example.postmate.posts.data.repository

import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.domain.repository.PostsRepository

class FakePostsRepositoryImpl: PostsRepository
{
    private val posts = mutableListOf<PostModelClass>()

    override suspend fun postsList(): List<PostModelClass> {
        return posts
    }

    fun addPost(postModelClass: PostModelClass) {
        posts.add(postModelClass)
    }

    fun clearPosts() {
        posts.clear()
    }

}
