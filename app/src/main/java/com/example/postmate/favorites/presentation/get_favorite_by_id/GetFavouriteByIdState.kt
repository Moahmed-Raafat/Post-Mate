package com.example.postmate.favorites.presentation.get_favorite_by_id

import com.example.postmate.favorites.data.remote.FavoriteModelClass

data class GetFavouriteByIdState(
    val isLoading: Boolean = false,
    val favorite: FavoriteModelClass ?= null,
    val error: String= ""
)
