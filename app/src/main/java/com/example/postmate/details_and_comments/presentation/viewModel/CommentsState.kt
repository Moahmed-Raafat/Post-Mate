package com.example.postmate.details_and_comments.presentation.viewModel

import com.example.postmate.details_and_comments.data.remote.CommentModelClass

data class CommentsState(
    val isLoading:Boolean= false,
    val response:List<CommentModelClass> = emptyList(),
    val error:String= ""
)