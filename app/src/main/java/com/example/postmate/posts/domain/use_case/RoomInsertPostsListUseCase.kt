package com.example.postmate.posts.domain.use_case

import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.domain.repository.RoomPostsRepository

class RoomInsertPostsListUseCase(private val roomPostsRepository: RoomPostsRepository)
{
    suspend operator fun invoke(posts: List<PostModelClass>)
    {
        roomPostsRepository.insertPostsList(posts)
    }
}