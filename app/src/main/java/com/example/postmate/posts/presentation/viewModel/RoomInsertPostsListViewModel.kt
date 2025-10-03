package com.example.postmate.posts.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.domain.use_case.RoomInsertPostsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomInsertPostsListViewModel(private val roomInsertPostsListUseCase: RoomInsertPostsListUseCase): ViewModel()
{
    fun insertPostsList(posts: List<PostModelClass>)=viewModelScope.launch(Dispatchers.IO)
    {
        roomInsertPostsListUseCase.invoke(posts)
    }

}