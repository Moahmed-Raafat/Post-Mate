package com.example.postmate.favorites.presentation.get_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmate.common.Resource
import com.example.postmate.favorites.domain.use_case.GetFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GetFavoritesViewModel(private val getFavouritesUseCase: GetFavoritesUseCase): ViewModel()
{
    private val _state= MutableStateFlow(GetFavoritesState())
    var state : StateFlow<GetFavoritesState> =_state

    init {
        getFavorites()
    }

    fun getFavorites()=viewModelScope.launch(Dispatchers.IO)
    {
        getFavouritesUseCase.invoke().collect {

            when (it) {
                is Resource.Loading -> {
                    _state.value = GetFavoritesState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = GetFavoritesState(favorites = it.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = GetFavoritesState(error = it.message ?: "Unknown error")
                }
            }

        }
    }
}