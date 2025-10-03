package com.example.postmate.posts.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmate.common.Resource
import com.example.postmate.posts.domain.use_case.PostsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostsViewModel(private val postsUseCase: PostsUseCase): ViewModel()
{
    private val _state= MutableStateFlow(PostsState())
    var state : StateFlow<PostsState> =_state

    init {
        getPosts()
    }

    fun getPosts()=viewModelScope.launch(Dispatchers.IO){
        postsUseCase().collect{
            when(it) {
                is Resource.Success -> {
                    _state.value= PostsState(posts = it.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value= PostsState(error = it.message?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value= PostsState(isLoading = true)
                }
            }
        }
    }
}