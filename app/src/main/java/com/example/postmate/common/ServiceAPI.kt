package com.example.postmate.common



import com.example.postmate.details_and_comments.data.remote.CommentModelClass
import com.example.postmate.posts.data.remote.PostModelClass
import retrofit2.http.GET
import retrofit2.http.Path


interface ServiceAPI {

    @GET("/posts")
    suspend fun getPosts() : List<PostModelClass>

    @GET("/posts/{post_id}/comments")
    suspend fun getComments(@Path("post_id") postId: Int) : List<CommentModelClass>
}