package com.example.postmate.favorites.domain.use_case

import com.example.postmate.common.Resource
import com.example.postmate.favorites.data.remote.FavoriteModelClass
import com.example.postmate.favorites.data.repository.FakeFavoritesRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.collections.forEach


class GetFavoriteByIdUseCaseTest {

    private lateinit var fakeFavoritesRepositoryImpl: FakeFavoritesRepositoryImpl
    private lateinit var getFavoriteByIdUseCase: GetFavoriteByIdUseCase
    private lateinit var insertFavouriteUseCase: InsertFavouriteUseCase

    @Before
    fun setUp()
    {
        fakeFavoritesRepositoryImpl= FakeFavoritesRepositoryImpl()
        getFavoriteByIdUseCase= GetFavoriteByIdUseCase(fakeFavoritesRepositoryImpl)
        insertFavouriteUseCase= InsertFavouriteUseCase(fakeFavoritesRepositoryImpl)


        runTest {
            fakeFavoritesRepositoryImpl.insertFavorite(
                FavoriteModelClass(1000,11,"favorites title 1","favorites body 1"))
        }
    }

    @Test
    fun `get favorite post from  room by id`() = runTest {

        val favoritePost = FavoriteModelClass(1000, 11, "favorites title 1", "favorites body 1")
        val emissions = getFavoriteByIdUseCase(11).toList()
        val successEmission = emissions.filterIsInstance<Resource.Success<FavoriteModelClass>>().firstOrNull()
        assertThat(successEmission?.data).isEqualTo(favoritePost)
    }

}