package com.example.postmate.posts.domain.repository

import com.example.postmate.posts.data.remote.PostModelClass

interface PostsRepository {
    suspend fun postsList() : List<PostModelClass>
}