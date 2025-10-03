package com.example.postmate.posts.presentation.viewModel

import com.example.postmate.posts.data.remote.PostModelClass

data class PostsState(
    val isLoading:Boolean= false,
    val posts:List<PostModelClass> = emptyList(),
    val error:String= ""
)
