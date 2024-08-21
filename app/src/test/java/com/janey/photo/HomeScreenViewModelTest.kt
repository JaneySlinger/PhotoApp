package com.janey.photo

import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.ui.homescreen.HomeEvent
import com.janey.photo.ui.homescreen.HomeScreenViewModel
import com.janey.photo.ui.homescreen.HomeState
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val fakeRespository: PhotoRepository = FakePhotoRepository()

    private lateinit var sut: HomeScreenViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        sut = HomeScreenViewModel(fakeRespository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel loads, data is loaded`() = runTest(testDispatcher) {
        sut = HomeScreenViewModel(fakeRespository)

        advanceUntilIdle()

        assertEquals(HomeState(photos = getSamplePhotoList(), searchTerm = "Yorkshire"), sut.state.value)
    }

    @Test
    fun`when search term is updated, state is updated`() = runTest(testDispatcher) {
        sut.handleEvent(HomeEvent.SearchFieldUpdated("Test"))

        assertEquals("Test", sut.state.value.searchTerm)
    }

    @Test
    fun `given search term has been updated, when search is clicked, new photos are loaded`() = runTest(testDispatcher) {
        sut.handleEvent(HomeEvent.SearchFieldUpdated("Test"))
        sut.handleEvent(HomeEvent.SearchClicked)

        advanceUntilIdle()

        assertEquals(HomeState(photos = getSamplePhotoList("Test"), searchTerm = "Test"), sut.state.value)
    }

    private fun getSamplePhotoList(searchTerm: String = "Yorkshire") = listOf(
        Photo(
            ownerId = "tortor",
            ownerName = searchTerm,
            iconServer = "omnesque",
            iconFarm = 4598,
            tags = "senectus",
            photoUrl = "https://www.google.com/#q=similique",
            title = "mei",
            description = Description(contentDescription = "moderatius"),
            dateTaken = "expetendis"
        )
    )

}