package com.example.postmate.favorites.presentation.delete_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmate.favorites.domain.use_case.DeleteFavoriteUseCase
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteFavoriteViewModel(private val deleteFavoriteUseCase: DeleteFavoriteUseCase): ViewModel()
{
    fun deleteFavorite(favoriteModelClass: FavoriteModelClass)= viewModelScope.launch(Dispatchers.IO)
    {
        deleteFavoriteUseCase.invoke(favoriteModelClass)
    }
}