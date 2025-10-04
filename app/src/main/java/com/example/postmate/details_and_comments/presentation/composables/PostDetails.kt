package com.example.postmate.details_and_comments.presentation.composables

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.postmate.R
import com.example.postmate.common.Constants
import com.example.postmate.details_and_comments.data.remote.CommentModelClass
import com.example.postmate.details_and_comments.presentation.viewModel.CommentsViewModel
import com.example.postmate.favorites.presentation.delete_favorite.DeleteFavoriteViewModel
import com.example.postmate.favorites.presentation.get_favorite_by_id.GetFavouriteByIdViewModel
import com.example.postmate.favorites.presentation.insert_favorite.InsertFavoriteViewModel
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import com.example.postmate.posts.presentation.composables.PostsList
import org.koin.androidx.compose.koinViewModel


@ExperimentalMaterial3Api
@Composable
fun PostDetails(userId: Int,
                id: Int,
                title: String,
                body: String,
                navController: NavController,
                commentsViewModel: CommentsViewModel = koinViewModel(),
                getFavoriteByIdViewModel: GetFavouriteByIdViewModel = koinViewModel(),
                insertFavoriteViewModel: InsertFavoriteViewModel = koinViewModel(),
                deleteFavoriteViewModel: DeleteFavoriteViewModel = koinViewModel())
{


    val state by commentsViewModel.state.collectAsState()
    val context= LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    //get the post from favorites if exists
    val getFavoriteByIdState by getFavoriteByIdViewModel.state.collectAsState()
    LaunchedEffect(id) {
        getFavoriteByIdViewModel.getFavoriteById(id)
    }
    LaunchedEffect(getFavoriteByIdState) {
        when {
            getFavoriteByIdState.isLoading -> {

            }
            getFavoriteByIdState.error.isNotEmpty() -> {

            }
            getFavoriteByIdState.favorite != null -> {
                isFavorite= true
            }
            else -> {
                isFavorite = false
            }
        }
    }


    Column (
        modifier = Modifier.fillMaxSize().background(color = colorResource( R.color.background)))
    {
        CenterAlignedTopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.post_details),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(R.color.primary),
                titleContentColor = colorResource(R.color.white)
            ),
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = colorResource(R.color.white)
                    )
                }
            },
            actions = {
                // Add an invisible spacer to balance the navigation icon
                Spacer(modifier = Modifier.size(48.dp))
            },
            modifier = Modifier.height(50.dp)
        )
        Column(modifier = Modifier.padding(10.dp).fillMaxSize())
        {

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){

                Icon(
                    Icons.Default.Favorite ,
                    contentDescription = stringResource(R.string.posts),
                    modifier = Modifier.size(35.dp).clickable{
                        if(isFavorite)
                        {
                            deleteFavoriteViewModel.deleteFavorite(
                                FavoriteModelClass(
                                    userId = userId,
                                    id = id,
                                    title = title,
                                    body = body))
                            Toast.makeText(context,"Post removed successfully from favourites", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            insertFavoriteViewModel.insertFavorite(
                                FavoriteModelClass(
                                    userId = userId,
                                    id = id,
                                    title = title,
                                    body = body))
                            Toast.makeText(context,"Post added successfully to favourites", Toast.LENGTH_SHORT).show()
                        }
                        isFavorite=!isFavorite
                    } ,
                    tint = if(isFavorite) {
                        colorResource(R.color.favorites)
                    }
                    else {
                        Color.Gray
                    }
                )
            }

            //title
            Text(
                text = title,
                color = colorResource(R.color.text),
                fontSize = 25.sp,
                modifier = Modifier.padding(horizontal = 10.dp),
                fontWeight = FontWeight.Bold

            )

            Spacer(Modifier.height(15.dp))

            //body
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.surface_card)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = body,
                        color = colorResource(R.color.text),
                        fontSize = 15.sp)
                }
            }


            Spacer(Modifier.height(20.dp))

            //comments
            Text(
                text = stringResource(R.string.comments),
                color = colorResource(R.color.text),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(5.dp))
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
                state.response.isNotEmpty() -> {
                    CommentsList(state.response)
                }
            }

        }
    }
}

@Composable
fun CommentsList(comments: List<CommentModelClass>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(comments) { comment ->
            commentItem(comment)
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun commentItem(comment: CommentModelClass)
{

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.surface_card)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row (modifier = Modifier.padding(12.dp))
        {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Top)
            {
                CircleWithText(comment.email)
            }

            Spacer(Modifier.width(8.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Top)
            {
                Text(
                    text = comment.name,
                    color = colorResource(R.color.text),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold

                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = comment.body,
                    color = colorResource(R.color.text),
                    fontSize = 15.sp
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = comment.email,
                    color = colorResource(R.color.secondary),
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic
                )

            }



        }
    }
}

@Composable
fun CircleWithText(text: String) {
    val colors = listOf(Color.Magenta, Color.Cyan, Color.Blue, Color.Magenta)
    val circleColor = remember { colors.random() }
    val char = text.firstOrNull()?.uppercaseChar() ?: ' '

    Canvas(modifier = Modifier.size(50.dp)) {
        val radius = size.minDimension / 2
        drawCircle(
            color = circleColor,
            radius = radius,
            center = center
        )
        val paint = Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = 60f
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        val fontMetrics = paint.fontMetrics
        val textHeight = fontMetrics.descent - fontMetrics.ascent
        val textOffset = textHeight / 2 - fontMetrics.descent

        drawContext.canvas.nativeCanvas.drawText(
            char.toString(),
            center.x,
            center.y + textOffset,
            paint
        )
    }
}