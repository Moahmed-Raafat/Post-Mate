package com.example.postmate.favorites.domain.use_case

import com.example.postmate.favorites.data.remote.FavoriteModelClass
import com.example.postmate.favorites.data.repository.FakeFavoritesRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetFavoritesUseCaseTest
{

    private lateinit var fakeFavoritesRepositoryImpl: FakeFavoritesRepositoryImpl
    private lateinit var getFavouriteUseCase: GetFavoritesUseCase

    private lateinit var favorites: MutableList<FavoriteModelClass>

    @Before
    fun setUp()
    {
        fakeFavoritesRepositoryImpl= FakeFavoritesRepositoryImpl()
        getFavouriteUseCase= GetFavoritesUseCase(fakeFavoritesRepositoryImpl)

        favorites= mutableListOf<FavoriteModelClass>()
        favorites.add(FavoriteModelClass(1000,10001,"favorites title 1","favorites body 1"))
        favorites.add(FavoriteModelClass(1000,10002,"favorites title 2","favorites body 2"))
        favorites.add(FavoriteModelClass(1000,10003,"favorites title 3","favorites body 3"))
        favorites.add(FavoriteModelClass(1000,10004,"favorites title 4","favorites body 4"))
        favorites.add(FavoriteModelClass(1000,10005,"favorites title 5","favorites body 5"))
        favorites.add(FavoriteModelClass(1000,10006,"favorites title 6","favorites body 6"))
        favorites.add(FavoriteModelClass(1000,10007,"favorites title 7","favorites body 7"))
        favorites.add(FavoriteModelClass(1000,10008,"favorites title 8","favorites body 8"))
        favorites.add(FavoriteModelClass(1000,10009,"favorites title 9","favorites body 9"))
        favorites.add(FavoriteModelClass(1000,10010,"favorites title 10","favorites body 10"))

        runTest {
            favorites.forEach{
                fakeFavoritesRepositoryImpl.insertFavorite(it)
            }
        }
    }

    @Test
    fun `posts come from room`() = runTest {

        // Skip the first emission (Loading) and collect the Success
        val result = getFavouriteUseCase().drop(1).first()
        val displayedPosts = result.data ?: emptyList()

        // posts from api get displayed
        assertThat(displayedPosts).isEqualTo(favorites)
    }

    @Test
    fun `no posts available in room`() = runTest {

        // Given: the API has no posts
        fakeFavoritesRepositoryImpl.clearFavorites()

        // When: skip Loading emission, collect the Success
        val result = getFavouriteUseCase().drop(1).first()
        val displayedPosts = result.data ?: emptyList()


        assertThat(displayedPosts).isEmpty()
    }
}