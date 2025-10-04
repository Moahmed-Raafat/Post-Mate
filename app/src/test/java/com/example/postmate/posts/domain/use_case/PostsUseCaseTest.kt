package com.example.postmate.posts.domain.use_case

import com.example.postmate.common.Constants
import com.example.postmate.posts.data.remote.PostModelClass
import com.example.postmate.posts.data.repository.FakePostsRepositoryImpl
import com.example.postmate.posts.data.repository.FakeRoomPostsRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.collections.mutableListOf

class PostsUseCaseTest {

    //posts from api
    private lateinit var fakePostsRepositoryImpl: FakePostsRepositoryImpl
    private lateinit var postsUseCase: PostsUseCase

    //posts from room
    private lateinit var fakeRoomPostsRepositoryImpl: FakeRoomPostsRepositoryImpl
    private lateinit var roomGetPostsUseCase: RoomGetPostsUseCase

    private lateinit var apiPosts: MutableList<PostModelClass>
    private lateinit var roomPosts: MutableList<PostModelClass>



    @Before
    fun setUp()
    {
        fakePostsRepositoryImpl= FakePostsRepositoryImpl()
        postsUseCase= PostsUseCase(fakePostsRepositoryImpl)

        fakeRoomPostsRepositoryImpl= FakeRoomPostsRepositoryImpl()
        roomGetPostsUseCase= RoomGetPostsUseCase(fakeRoomPostsRepositoryImpl)


        apiPosts= mutableListOf<PostModelClass>()
        apiPosts.add(PostModelClass(1000,10001,"api title 1","api body 1"))
        apiPosts.add(PostModelClass(1000,10002,"api title 2","api body 2"))
        apiPosts.add(PostModelClass(1000,10003,"api title 3","api body 3"))
        apiPosts.add(PostModelClass(1000,10004,"api title 4","api body 4"))
        apiPosts.add(PostModelClass(1000,10005,"api title 5","api body 5"))
        apiPosts.add(PostModelClass(1000,10006,"api title 6","api body 6"))
        apiPosts.add(PostModelClass(1000,10007,"api title 7","api body 7"))
        apiPosts.add(PostModelClass(1000,10008,"api title 8","api body 8"))
        apiPosts.add(PostModelClass(1000,10009,"api title 9","api body 9"))
        apiPosts.add(PostModelClass(1000,10010,"api title 10","api body 10"))


        roomPosts= mutableListOf<PostModelClass>()
        roomPosts.add(PostModelClass(1000,10001,"room title 1","room body 1"))
        roomPosts.add(PostModelClass(1000,10002,"room title 2","room body 2"))
        roomPosts.add(PostModelClass(1000,10003,"room title 3","room body 3"))
        roomPosts.add(PostModelClass(1000,10004,"room title 4","room body 4"))
        roomPosts.add(PostModelClass(1000,10005,"room title 5","room body 5"))
        roomPosts.add(PostModelClass(1000,10006,"room title 6","room body 6"))
        roomPosts.add(PostModelClass(1000,10007,"room title 7","room body 7"))
        roomPosts.add(PostModelClass(1000,10008,"room title 8","room body 8"))
        roomPosts.add(PostModelClass(1000,10009,"room title 9","room body 9"))
        roomPosts.add(PostModelClass(1000,10010,"room title 10","room body 10"))


        runTest {
            apiPosts.forEach{
                fakePostsRepositoryImpl.addPost(it)
            }

            fakeRoomPostsRepositoryImpl.insertPostsList(roomPosts)
        }
    }


    @Test
    fun `when network is available posts come from API`() = runTest {

        // Skip the first emission (Loading) and collect the Success
        val result = postsUseCase().drop(1).first()
        val displayedPosts = result.data ?: emptyList()

        // posts from api get displayed
        assertThat(displayedPosts).isEqualTo(apiPosts)
        assertThat(displayedPosts).isNotEqualTo(roomPosts)
    }

    @Test
    fun `when network is available but no posts available`() = runTest {

        // Given: the API has no posts
        fakePostsRepositoryImpl.clearPosts()

        // When: skip Loading emission, collect the Success
        val result = postsUseCase().drop(1).first()
        val displayedPosts = result.data ?: emptyList()


        assertThat(displayedPosts).isEmpty()
    }


    @Test
    fun `when network is not available posts come from room`() = runTest {

        // Skip the first emission (Loading) and collect the Success
        val result = roomGetPostsUseCase().drop(1).first()
        val displayedPosts = result.data ?: emptyList()

        // posts from api get displayed
        assertThat(displayedPosts).isEqualTo(roomPosts)
        assertThat(displayedPosts).isNotEqualTo(apiPosts)
    }

    @Test
    fun `when network is not available but no posts available in room`() = runTest {

        // Given: the API has no posts
        fakeRoomPostsRepositoryImpl.clearPosts()

        // When: skip Loading emission, collect the Success
        val result = roomGetPostsUseCase().drop(1).first()
        val displayedPosts = result.data ?: emptyList()


        assertThat(displayedPosts).isEmpty()
    }

}