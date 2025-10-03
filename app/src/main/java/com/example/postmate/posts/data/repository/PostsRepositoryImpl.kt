package com.example.postmate.posts.data.repository

import com.example.postmate.common.ServiceAPI
import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.domain.repository.PostsRepository

class PostsRepositoryImpl(private val serviceAPI: ServiceAPI): PostsRepository
{
    override suspend fun postsList(): List<PostModelClass> {
        return serviceAPI.getPosts()
    }

}