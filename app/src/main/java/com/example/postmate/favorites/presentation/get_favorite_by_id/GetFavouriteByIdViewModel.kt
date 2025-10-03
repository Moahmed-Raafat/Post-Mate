package com.example.postmate.favorites.presentation.get_favorite_by_id

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmate.common.Resource
import com.example.postmate.favorites.domain.use_case.GetFavoriteByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GetFavouriteByIdViewModel(private val getFavoriteByIdUseCase: GetFavoriteByIdUseCase): ViewModel()
{

    private val _state= MutableStateFlow(GetFavouriteByIdState())
    var state : StateFlow<GetFavouriteByIdState> =_state


    fun getFavoriteById(id: Int)=viewModelScope.launch(Dispatchers.IO)
    {
        getFavoriteByIdUseCase.invoke(id).collect {

            _state.value = when (it) {
                is Resource.Loading -> GetFavouriteByIdState(isLoading = true)
                is Resource.Success -> GetFavouriteByIdState(favorite = it.data )
                is Resource.Error -> GetFavouriteByIdState(error = it.message ?: "Unknown error")
            }
        }
    }

}