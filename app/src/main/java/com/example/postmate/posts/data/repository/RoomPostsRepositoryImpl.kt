package com.example.postmate.posts.data.repository

import com.example.postmate.database.PostDao
import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.domain.repository.RoomPostsRepository
import kotlinx.coroutines.flow.Flow

class RoomPostsRepositoryImpl(private val postsDao: PostDao) : RoomPostsRepository
{
    override fun getPosts(): Flow<List<PostModelClass>> {
        return postsDao.getPosts()
    }

    override suspend fun insertPostsList(posts: List<PostModelClass>) {
        return postsDao.insertPostsList(posts)
    }

}