package com.janey.photo

import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.ui.homeScreen.HomeScreenViewModel
import com.janey.photo.ui.homeScreen.HomeState
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

        assertEquals(HomeState(photos = photoList, searchTerm = "Yorkshire"), sut.state.value)
    }

    companion object {
        val photoList = listOf(
            Photo(
                ownerId = "tortor",
                ownerName = "Eliza Dudley",
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

}