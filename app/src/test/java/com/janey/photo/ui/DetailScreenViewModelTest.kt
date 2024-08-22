package com.janey.photo.ui

import androidx.lifecycle.SavedStateHandle
import com.janey.photo.data.FakePhotoRepository
import com.janey.photo.data.PhotoRepository
import com.janey.photo.ui.detailscreen.DetailScreenViewModel
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
class DetailScreenViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()

    private val fakeRespository: PhotoRepository = FakePhotoRepository()

    private lateinit var sut: DetailScreenViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val savedState = SavedStateHandle(mapOf("id" to "1234"))
        sut = DetailScreenViewModel(
            savedStateHandle = savedState,
            fakeRespository,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel initialises, photo data is loaded from repository`() = runTest(testDispatcher) {
        advanceUntilIdle()

        assertEquals("tag1 tag2 tag3", sut.state.value.tags)
        assertEquals("https://www.google.com/#q=similique", sut.state.value.url)
        assertEquals("mei", sut.state.value.title)
        assertEquals("Owner", sut.state.value.userName)
        assertEquals("https://farm4598.staticflickr.com/omnesque/buddyicons/tortor.jpg", sut.state.value.profileUrl)
        assertEquals("expetendis", sut.state.value.dateTaken)
        assertEquals("moderatius", sut.state.value.description)
    }
}