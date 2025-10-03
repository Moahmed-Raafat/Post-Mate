package com.example.postmate.favorites.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.postmate.R
import com.example.postmate.favorites.presentation.delete_favorite.DeleteFavoriteViewModel
import com.example.postmate.favorites.presentation.get_favorites.GetFavoritesViewModel
import com.example.postmate.navigation.Screens
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun Favorites(
    navController: NavController,
    getFavoritesViewModel: GetFavoritesViewModel = koinViewModel(),
    deleteFavoriteViewModel: DeleteFavoriteViewModel = koinViewModel()
)
{
    val context= LocalContext.current
    val state by getFavoritesViewModel.state.collectAsState()


    LaunchedEffect(state) {
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    Column (
        modifier = Modifier.fillMaxSize().background(color = colorResource( R.color.background)))
    {
        TopAppBar(
            modifier = Modifier.height(60.dp),
            title= {

                Text(
                    text= stringResource(R.string.favorites),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center)

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(R.color.primary),
                titleContentColor = colorResource(R.color.white)
            )
        )

        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        CircularProgressIndicator()
                    }
                }
                state.favorites.isNotEmpty() -> {
                    FavoritesList(posts = state.favorites,navController,deleteFavoriteViewModel)
                }
                state.favorites.isEmpty() -> {
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text( text = "No Favorites Yet", color = colorResource(R.color.text))
                    }

                }
            }
        }
    }

}

@Composable
fun FavoritesList(posts: List<FavoriteModelClass>,
                  navController: NavController,
                  deleteFavoriteViewModel: DeleteFavoriteViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(posts) { post ->
            PostItem(post,navController,deleteFavoriteViewModel)
        }
    }
}

@Composable
fun PostItem(post: FavoriteModelClass,
             navController: NavController,
             deleteFavoriteViewModel: DeleteFavoriteViewModel)
{

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().clickable{
            navController.navigate(Screens.PostDetails.route +
                    "/${post.userId}/${post.id}/${post.title}/${post.body}")
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.surface_card)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {

                Column(modifier = Modifier.weight(1f)) {
                    ShowTitle(
                    text = post.title,
                    color = colorResource(R.color.text),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )  }

                //delete
                Icon(
                    Icons.Default.Delete ,
                    contentDescription = stringResource(R.string.favorites),
                    modifier = Modifier.size(26.dp).clickable{
                        deleteFavoriteViewModel.deleteFavorite(post)
                    },
                    tint =  Color.Red
                )
            }


            Spacer(Modifier.height(8.dp))

            Text(
                text = post.body,
                color = colorResource(R.color.text),
                fontSize = 15.sp,
                maxLines = if (expanded) Int.MAX_VALUE else 4
            )

            //see more
            Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                if (!expanded) {
                    Text(
                        text = stringResource(R.string.see_more),
                        color = colorResource(R.color.secondary),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable { expanded = true }
                    )
                }
            }
        }
    }
}

@Composable
fun ShowTitle(
    text: String,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    maxWords: Int = 7
) {
    val displayText = remember(text, maxWords) {
        val words = text.split("\\s+".toRegex())
        if (words.size > maxWords) {
            words.take(maxWords).joinToString(" ") + " ..."
        } else {
            text
        }
    }

    Text(
        text = displayText,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        maxLines = Int.MAX_VALUE
    )
}

