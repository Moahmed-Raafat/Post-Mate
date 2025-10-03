package com.example.postmate.favorites.presentation.get_favorites

import com.example.postmate.favorites.data.remote.FavoriteModelClass

data class GetFavoritesState(
    var isLoading:Boolean= false,
    val favorites: List<FavoriteModelClass> = emptyList(),
    var error:String= ""
)
