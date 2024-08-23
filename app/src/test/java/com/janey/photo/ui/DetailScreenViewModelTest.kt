package com.janey.photo.ui

import androidx.lifecycle.SavedStateHandle
import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.ui.detailscreen.DetailScreenViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock

class DetailScreenViewModelTest {

    private val mockRepository: PhotoRepository = mock {
        on { getPhotoById(any()) } doAnswer {
            Photo(
                id = "1234",
                ownerId = "tortor",
                ownerName = "Owner",
                iconServer = "omnesque",
                iconFarm = 4598,
                tags = "tag1 tag2 tag3",
                photoUrl = "https://www.google.com/#q=similique",
                title = "mei",
                description = Description(contentDescription = "moderatius"),
                dateTaken = "expetendis"
            )
        }
    }

    private lateinit var sut: DetailScreenViewModel

    @Before
    fun setup() {
        val savedState = SavedStateHandle(mapOf("id" to "1234"))
        sut = DetailScreenViewModel(
            savedStateHandle = savedState,
            mockRepository,
        )
    }

    @Test
    fun `when viewModel initialises, photo data is loaded from repository`() {

        assertEquals("tag1 tag2 tag3", sut.state.value.tags)
        assertEquals("https://www.google.com/#q=similique", sut.state.value.url)
        assertEquals("mei", sut.state.value.title)
        assertEquals("Owner", sut.state.value.userName)
        assertEquals(
            "https://farm4598.staticflickr.com/omnesque/buddyicons/tortor.jpg",
            sut.state.value.profileUrl
        )
        assertEquals("expetendis", sut.state.value.dateTaken)
        assertEquals("moderatius", sut.state.value.description)
    }
}