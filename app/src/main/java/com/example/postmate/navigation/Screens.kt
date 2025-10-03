package com.example.postmate.navigation

import com.example.postmate.common.Constants


sealed class Screens (val route:String)
{
    data object Posts:Screens(Constants.POSTS)
    data object PostDetails:Screens(Constants.POST_DETAILS)
    data object Favorites:Screens(Constants.FAVORITES)
}