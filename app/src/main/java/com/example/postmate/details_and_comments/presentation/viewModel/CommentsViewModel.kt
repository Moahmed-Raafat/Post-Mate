package com.example.postmate.details_and_comments.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmate.details_and_comments.domain.use_case.CommentsUseCase
import com.example.postmate.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommentsViewModel(private val commentsUseCase: CommentsUseCase,
                        savedStateHandle: SavedStateHandle): ViewModel()
{
    private val _state= MutableStateFlow(CommentsState())
    var state : StateFlow<CommentsState> =_state

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            getComments(id)
        }
    }

    fun getComments(postId: Int)= viewModelScope.launch(Dispatchers.IO){
        commentsUseCase(postId).collect{
            when(it) {
                is Resource.Success -> {
                    _state.value= CommentsState(response = it.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value=
                        CommentsState(error = it.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value= CommentsState(isLoading = true)
                }
            }
        }
    }

}