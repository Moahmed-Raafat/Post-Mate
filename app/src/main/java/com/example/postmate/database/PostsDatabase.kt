package com.example.postmate.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import com.example.postmate.posts.data.remote.PostModelClass

@Database(
    entities = [PostModelClass::class , FavoriteModelClass::class],
    version = 1)
abstract class PostsDatabase: RoomDatabase()
{
    abstract val postDao: PostDao

    companion object{

        //name of the database
        const val DATABASE_NAME = "notes_db"
    }
}