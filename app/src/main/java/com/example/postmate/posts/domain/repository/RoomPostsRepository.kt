package com.example.postmate.posts.domain.repository

import com.example.postmate.posts.data.remote.PostModelClass
import kotlinx.coroutines.flow.Flow

interface RoomPostsRepository {

    fun getPosts(): Flow<List<PostModelClass>>

    suspend fun insertPostsList(posts : List<PostModelClass>)

}