package com.example.postmate.navigation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.postmate.R
import com.example.postmate.details_and_comments.presentation.composables.PostDetails
import com.example.postmate.favorites.presentation.Favorites
import com.example.postmate.posts.presentation.composables.Posts


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BottomSheet() {

    val navController  = rememberNavController()
    var selected by remember { mutableStateOf(Icons.Default.Home) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if(currentRoute != Screens.PostDetails.route + "/{userId}/{id}/{title}/{body}")
            {
                BottomAppBar(containerColor = colorResource(R.color.primary))
                {
                    //posts
                    IconButton(
                        onClick= {

                            selected = Icons.Default.Home
                            navController.navigate(Screens.Posts.route){
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f))
                    {

                        Icon(
                            Icons.Default.Home ,
                            contentDescription = stringResource(R.string.posts),
                            modifier = Modifier.size(26.dp) ,
                            tint = if(selected == Icons.Default.Home) {
                                Color.White
                            }
                            else {
                                Color.Gray
                            }
                        )
                    }

                    //favourites
                    IconButton(
                        onClick= {
                            selected = Icons.Default.Favorite
                            navController.navigate(Screens.Favorites.route){
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.weight(1f))
                    {
                        Icon(
                            Icons.Default.Favorite ,
                            contentDescription = stringResource(R.string.favorites),
                            modifier = Modifier.size(26.dp) ,
                            tint = if(selected == Icons.Default.Favorite)
                            {
                                Color.White
                            }
                            else
                            {
                                Color.Gray
                            }

                        )
                    }

                }
            }

        }
    )
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding),)
        {

            NavHost(
                navController = navController ,
                startDestination = Screens.Posts.route,
                modifier = Modifier.fillMaxSize())
            {
                composable(Screens.Posts.route) { Posts(navController ) }
                composable(Screens.Favorites.route) { Favorites(navController) }
                composable(
                    route = Screens.PostDetails.route + "/{userId}/{id}/{title}/{body}",
                    arguments = listOf(
                        navArgument("userId") { type = NavType.IntType },
                        navArgument("id") { type = NavType.IntType },
                        navArgument("title") { type = NavType.StringType },
                        navArgument("body") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getInt("userId") ?: -1
                    val id = backStackEntry.arguments?.getInt("id") ?: -1
                    val title = backStackEntry.arguments?.getString("title") ?: ""
                    val body = backStackEntry.arguments?.getString("body") ?: ""
                    PostDetails(userId, id, title, body, navController)
                }
            }

        }
    }
}