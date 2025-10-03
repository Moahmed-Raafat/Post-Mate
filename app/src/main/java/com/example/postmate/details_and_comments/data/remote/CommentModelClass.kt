package com.example.postmate.details_and_comments.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentModelClass(
    @SerializedName("postId")
    @Expose
    val postId: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("body")
    @Expose
    val body: String
)
