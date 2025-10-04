package com.example.postmate.favorites.domain.use_case

import com.example.postmate.favorites.data.remote.FavoriteModelClass
import com.example.postmate.favorites.data.repository.FakeFavoritesRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.collections.forEach


class InsertFavouriteUseCaseTest {

    private lateinit var fakeFavoritesRepositoryImpl: FakeFavoritesRepositoryImpl
    private lateinit var getFavouriteUseCase: GetFavoritesUseCase
    private lateinit var insertFavouriteUseCase: InsertFavouriteUseCase

    private lateinit var favorites: MutableList<FavoriteModelClass>

    @Before
    fun setUp()
    {
        fakeFavoritesRepositoryImpl= FakeFavoritesRepositoryImpl()
        getFavouriteUseCase= GetFavoritesUseCase(fakeFavoritesRepositoryImpl)
        insertFavouriteUseCase= InsertFavouriteUseCase(fakeFavoritesRepositoryImpl)

        favorites= mutableListOf<FavoriteModelClass>()
        favorites.add(FavoriteModelClass(1000,10001,"favorites title 1","favorites body 1"))
        favorites.add(FavoriteModelClass(1000,10002,"favorites title 2","favorites body 2"))


        runTest {
            favorites.forEach{
                fakeFavoritesRepositoryImpl.insertFavorite(it)
            }
        }
    }

    @Test
    fun `adding favorite post to favorites`() = runTest {

        //add to favorites
        val favorite= FavoriteModelClass(1000,10001,"favorites title 1","favorites body 1")
        insertFavouriteUseCase.invoke(favorite)


        //get favorites and make sure it has the favorite post added before
        // Skip the first emission (Loading) and collect the Success
        val result = getFavouriteUseCase().drop(1).first()
        val displayedPosts = result.data ?: emptyList()

        // posts from api get displayed
        assertThat(displayedPosts).contains(favorite)
    }

}