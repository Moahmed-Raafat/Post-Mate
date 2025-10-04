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


class DeleteFavoriteUseCaseTest {
    private lateinit var fakeFavoritesRepositoryImpl: FakeFavoritesRepositoryImpl
    private lateinit var getFavouriteUseCase: GetFavoritesUseCase
    private lateinit var deleteFavoriteUseCase: DeleteFavoriteUseCase

    private lateinit var favorites: MutableList<FavoriteModelClass>

    @Before
    fun setUp()
    {
        fakeFavoritesRepositoryImpl= FakeFavoritesRepositoryImpl()
        getFavouriteUseCase= GetFavoritesUseCase(fakeFavoritesRepositoryImpl)
        deleteFavoriteUseCase= DeleteFavoriteUseCase(fakeFavoritesRepositoryImpl)

        favorites= mutableListOf<FavoriteModelClass>()
        favorites.add(FavoriteModelClass(1000,10001,"favorites title 1","favorites body 1"))
        favorites.add(FavoriteModelClass(1000,10002,"favorites title 2","favorites body 2"))
        favorites.add(FavoriteModelClass(1000,10003,"favorites title 3","favorites body 3"))
        favorites.add(FavoriteModelClass(1000,10004,"favorites title 4","favorites body 4"))
        favorites.add(FavoriteModelClass(1000,10005,"favorites title 5","favorites body 5"))


        runTest {
            favorites.forEach{
                fakeFavoritesRepositoryImpl.insertFavorite(it)
            }
        }
    }

    @Test
    fun `delete one favorite post from favorites in room`() = runTest {

        val favoritePostToDelete= FavoriteModelClass(1000,10005,"favorites title 5","favorites body 5")

        //delete the favorite post
        deleteFavoriteUseCase.invoke(favoritePostToDelete)

        // Skip the first emission (Loading) and collect the Success
        val result = getFavouriteUseCase().drop(1).first()
        val displayedPosts = result.data ?: emptyList()

        // posts from api get displayed
        assertThat(displayedPosts).doesNotContain(favoritePostToDelete)
    }

}