package com.tholh.mvp.feature.home.presenter

import com.tholh.mvp.feature.home.view.HomeView
import com.tholh.mvp.model.MovieList
import com.tholh.mvp.repository.MovieRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
 * Created by tholh on 09/04/2021.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomePresenterImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var homeView: HomeView

    @Mock
    lateinit var movieRepository: MovieRepository

    lateinit var homePresenter: HomePresenterImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        homePresenter = HomePresenterImpl(homeView, movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    fun testLoadData() {

    }

    @Test
    fun testLoadTrending() = runBlockingTest {
        val movieList = MovieList(1, ArrayList(), 2, 20)
        `when`(
            movieRepository.getTrending(
                mediaType = anyString(),
                timeWindow = anyString(),
                apiKey = anyString(),
                page = anyInt()
            )
        ).thenReturn(Response.success(movieList))
        homePresenter.loadTrending()
        runBlocking {
            homePresenter.coroutineContext.job.children.forEach { it.join() }
        }
        verify(homeView).showLoadMoreTrending(true)
        verify(homeView).showLoadMoreTrending(false)
        verify(homeView).bindTrendingData(anyObject())
    }

    @Test
    fun testLoadTrendingFailed() = runBlockingTest {
        val responseBody = "".toResponseBody()
        `when`(
            movieRepository.getTrending(
                mediaType = anyString(),
                timeWindow = anyString(),
                apiKey = anyString(),
                page = anyInt()
            )
        ).thenReturn(Response.error(404, responseBody))
        homePresenter.loadTrending()
        runBlocking {
            homePresenter.coroutineContext.job.children.forEach { it.join() }
        }
        verify(homeView).showLoadMoreTrending(true)
        verify(homeView).showLoadMoreTrending(false)
        verify(homeView).showGenericError()
    }

    @Test
    fun testLoadPopular() = runBlockingTest {
        val movieList = MovieList(1, ArrayList(), 2, 20)
        `when`(
            movieRepository.getPopular(
                apiKey = org.mockito.kotlin.any(),
                language = org.mockito.kotlin.any(),
                region = org.mockito.kotlin.any(),
                page = anyInt()
            )
        ).thenReturn(Response.success(movieList))
        homePresenter.loadPopular()
        runBlocking {
            homePresenter.coroutineContext.job.children.forEach { it.join() }
        }
        verify(homeView).showLoadMorePopular(true)
        verify(homeView).showLoadMorePopular(false)
        verify(homeView).bindPopularData(anyObject())
    }

    @Test
    fun testLoadPopularFailed() = runBlockingTest {
        val responseBody = "".toResponseBody()
        `when`(
            movieRepository.getPopular(
                apiKey = org.mockito.kotlin.any(),
                language = org.mockito.kotlin.any(),
                region = org.mockito.kotlin.any(),
                page = anyInt()
            )
        ).thenReturn(Response.error(404, responseBody))
        homePresenter.loadPopular()
        runBlocking {
            homePresenter.coroutineContext.job.children.forEach { it.join() }
        }
        verify(homeView).showLoadMorePopular(true)
        verify(homeView).showLoadMorePopular(false)
        verify(homeView).showGenericError()
    }

    @Test
    fun testLoadTopRated() = runBlockingTest {
        val movieList = MovieList(1, ArrayList(), 2, 20)
        `when`(
            movieRepository.getTopRated(
                apiKey = org.mockito.kotlin.any(),
                language = org.mockito.kotlin.any(),
                region = org.mockito.kotlin.any(),
                page = anyInt()
            )
        ).thenReturn(Response.success(movieList))
        homePresenter.loadTopRated()
        runBlocking {
            homePresenter.coroutineContext.job.children.forEach { it.join() }
        }
        verify(homeView).showLoadMoreTopRated(true)
        verify(homeView).showLoadMoreTopRated(false)
        verify(homeView).bindTopRatedData(anyObject())
    }

    @Test
    fun testLoadTopRatedFailed() = runBlockingTest {
        val responseBody = "".toResponseBody()
        `when`(
            movieRepository.getTopRated(
                apiKey = org.mockito.kotlin.any(),
                language = org.mockito.kotlin.any(),
                region = org.mockito.kotlin.any(),
                page = anyInt()
            )
        ).thenReturn(Response.error(404, responseBody))
        homePresenter.loadTopRated()
        runBlocking {
            homePresenter.coroutineContext.job.children.forEach { it.join() }
        }
        verify(homeView).showLoadMoreTopRated(true)
        verify(homeView).showLoadMoreTopRated(false)
        verify(homeView).showGenericError()
    }

    @Test
    fun testLoadUpcoming() = runBlockingTest {
        val movieList = MovieList(1, ArrayList(), 2, 20)
        `when`(
            movieRepository.getUpcoming(
                apiKey = org.mockito.kotlin.any(),
                language = org.mockito.kotlin.any(),
                region = org.mockito.kotlin.any(),
                page = anyInt()
            )
        ).thenReturn(Response.success(movieList))
        homePresenter.loadUpcoming()
        runBlocking {
            homePresenter.coroutineContext.job.children.forEach { it.join() }
        }
        verify(homeView).showLoadMoreUpcoming(true)
        verify(homeView).showLoadMoreUpcoming(false)
        verify(homeView).bindUpcomingData(anyObject())
    }

    @Test
    fun testLoadUpcomingFailed() = runBlockingTest {
        val responseBody = "".toResponseBody()
        `when`(
            movieRepository.getUpcoming(
                apiKey = org.mockito.kotlin.any(),
                language = org.mockito.kotlin.any(),
                region = org.mockito.kotlin.any(),
                page = anyInt()
            )
        ).thenReturn(Response.error(404, responseBody))
        homePresenter.loadUpcoming()
        runBlocking {
            homePresenter.coroutineContext.job.children.forEach { it.join() }
        }
        verify(homeView).showLoadMoreUpcoming(true)
        verify(homeView).showLoadMoreUpcoming(false)
        verify(homeView).showGenericError()
    }

}