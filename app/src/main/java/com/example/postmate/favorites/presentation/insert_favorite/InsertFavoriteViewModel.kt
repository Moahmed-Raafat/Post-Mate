package com.example.postmate.favorites.presentation.insert_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postmate.favorites.domain.use_case.InsertFavouriteUseCase
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsertFavoriteViewModel(private val insertFavoriteUseCase: InsertFavouriteUseCase): ViewModel()
{
    fun insertFavorite(favoriteModelClass: FavoriteModelClass)=viewModelScope.launch(Dispatchers.IO)
    {
        insertFavoriteUseCase.invoke(favoriteModelClass)
    }
}