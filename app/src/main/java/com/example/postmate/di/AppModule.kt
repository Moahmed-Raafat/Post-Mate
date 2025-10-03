package com.example.postmate.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.example.postmate.details_and_comments.data.repository.CommentsRepositoryImpl
import com.example.postmate.details_and_comments.domain.repository.CommentsRepository
import com.example.postmate.details_and_comments.domain.use_case.CommentsUseCase
import com.example.postmate.details_and_comments.presentation.viewModel.CommentsViewModel
import com.example.postmate.common.Constants
import com.example.postmate.common.ServiceAPI
import com.example.postmate.common.netwrork_and_internet.NetworkViewModel
import com.example.postmate.database.PostsDatabase
import com.example.postmate.favorites.data.repository.FavoritesRepositoryImpl
import com.example.postmate.favorites.domain.repository.FavoritesRepository
import com.example.postmate.favorites.domain.use_case.DeleteFavoriteUseCase
import com.example.postmate.favorites.domain.use_case.GetFavoriteByIdUseCase
import com.example.postmate.favorites.domain.use_case.GetFavoritesUseCase
import com.example.postmate.favorites.domain.use_case.InsertFavouriteUseCase
import com.example.postmate.favorites.presentation.delete_favorite.DeleteFavoriteViewModel
import com.example.postmate.favorites.presentation.get_favorite_by_id.GetFavouriteByIdViewModel
import com.example.postmate.favorites.presentation.get_favorites.GetFavoritesViewModel
import com.example.postmate.favorites.presentation.insert_favorite.InsertFavoriteViewModel
import com.example.postmate.posts.data.repository.PostsRepositoryImpl
import com.example.postmate.posts.data.repository.RoomPostsRepositoryImpl
import com.example.postmate.posts.domain.repository.PostsRepository
import com.example.postmate.posts.domain.repository.RoomPostsRepository
import com.example.postmate.posts.domain.use_case.PostsUseCase
import com.example.postmate.posts.domain.use_case.RoomGetPostsUseCase
import com.example.postmate.posts.domain.use_case.RoomInsertPostsListUseCase
import com.example.postmate.posts.presentation.viewModel.PostsViewModel
import com.example.postmate.posts.presentation.viewModel.RoomGetPostsViewModel
import com.example.postmate.posts.presentation.viewModel.RoomInsertPostsListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule= module{

    //networking

    // 1- HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // 2- OkHttpClient (secure, no trust-all-certs hack)
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    // 3- Retrofit Api
    single<ServiceAPI> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiceAPI::class.java)
    }

    /////////////////////////////////////////////////////////////////////
    //room database
    single {
        Room.databaseBuilder(
            get(),
            PostsDatabase::class.java,
            PostsDatabase.DATABASE_NAME
        ).build()
    }

    single { get<PostsDatabase>().postDao }

    /////////////////////////////////////////////////////////////////////
    //posts
    // Repository
    single<PostsRepository> { PostsRepositoryImpl(get()) }

    // UseCase
    factory { PostsUseCase(get()) }

    // ViewModel
    viewModel { PostsViewModel(get()) }

    /////////////////////////////////////////////////////////////////////
    //comments
    // Repository
    single<CommentsRepository> { CommentsRepositoryImpl(get()) }

    // UseCase
    factory { CommentsUseCase(get()) }

    // ViewModel
    viewModel { (handle: SavedStateHandle) ->
        CommentsViewModel(get(), handle)
    }


    ///////////////////////////////////////////////////////////////////
    // favorites

    single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }

    //get favorites
    factory { GetFavoritesUseCase(get()) }
    viewModel { GetFavoritesViewModel(get()) }

    //add favorites
    factory { InsertFavouriteUseCase(get()) }
    viewModel { InsertFavoriteViewModel(get()) }

    //get favorite by id
    factory { GetFavoriteByIdUseCase(get()) }
    viewModel { GetFavouriteByIdViewModel(get()) }

    //delete favorite\
    factory { DeleteFavoriteUseCase(get()) }
    viewModel { DeleteFavoriteViewModel(get()) }

    /////////////////////////////////////////////////////////////////////
    //posts in room database

    single<RoomPostsRepository> { RoomPostsRepositoryImpl(get()) }

    //get favorites
    factory { RoomGetPostsUseCase(get()) }
    viewModel { RoomGetPostsViewModel(get()) }

    //add posts list
    factory { RoomInsertPostsListUseCase(get()) }
    viewModel { RoomInsertPostsListViewModel(get()) }

    /////////////////////////////////////////////////////////////////////
    //network and internet

    viewModel { NetworkViewModel(androidContext()) }
}