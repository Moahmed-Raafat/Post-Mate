package com.example.postmate.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import com.example.postmate.posts.data.remote.PostModelClass
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    //posts

    @Query("SELECT * FROM posts")
    fun getPosts(): Flow<List<PostModelClass>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostsList(posts: List<PostModelClass>)

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //favorites

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteModelClass>>

    @Query("SELECT * FROM favorites WHERE id=:id")
    suspend fun getFavoritesById(id : Int):FavoriteModelClass

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(post : FavoriteModelClass)

    @Delete
    suspend fun deleteFavorite(post : FavoriteModelClass)
}