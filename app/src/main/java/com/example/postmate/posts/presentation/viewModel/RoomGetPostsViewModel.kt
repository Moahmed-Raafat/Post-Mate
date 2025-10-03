package com.example.postmate.posts.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmate.common.Resource
import com.example.postmate.posts.domain.use_case.RoomGetPostsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomGetPostsViewModel (private val roomGetPostsUseCase: RoomGetPostsUseCase): ViewModel()
{
    private val _state= MutableStateFlow(PostsState())
    var state : StateFlow<PostsState> =_state


    fun getPostsFromRoom()=viewModelScope.launch(Dispatchers.IO)
    {
        roomGetPostsUseCase.invoke().collect {

            when (it) {
                is Resource.Loading -> {
                    _state.value = PostsState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = PostsState(posts = it.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = PostsState(error = it.message ?: "Unknown error")
                }
            }

        }
    }
}