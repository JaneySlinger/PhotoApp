package com.janey.photo.ui

import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.SearchType
import com.janey.photo.network.model.TagType
import com.janey.photo.ui.homescreen.HomeEvent
import com.janey.photo.ui.homescreen.HomeScreenViewModel
import com.janey.photo.ui.homescreen.HomeState
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class HomeScreenViewModelTest {

    private val mockRepository: PhotoRepository = mock()

    private lateinit var sut: HomeScreenViewModel

    @Before
    fun setup() {
        sut = HomeScreenViewModel(mockRepository)
    }

    @Test
    fun `when viewModel loads, searchTerm is Yorkshire and search type is term`() {
        sut = HomeScreenViewModel(mockRepository)

        assertEquals(
            HomeState(
                searchTerm = "Yorkshire",
                searchType = SearchType.Term("Yorkshire")
            ), sut.state.value
        )
    }

    @Test
    fun `when search clicked, search type is updated`() {
        sut.handleEvent(HomeEvent.SearchFieldUpdated("Nottingham"))
        sut.handleEvent(HomeEvent.SearchClicked)

        assertEquals(
            HomeState(
                searchTerm = "Nottingham",
                searchType = SearchType.Term("Nottingham")
            ), sut.state.value
        )
    }

    @Test
    fun `when search term updated, search term in state is updated`() {
        sut.handleEvent(HomeEvent.SearchFieldUpdated("Nottingham"))

        assertEquals(
            HomeState(
                searchTerm = "Nottingham",
                searchType = SearchType.Term("Yorkshire")
            ), sut.state.value
        )
    }

    @Test
    fun `when user clicked, search term is updated and search type is user`() {
        sut.handleEvent(HomeEvent.UserClicked("username", "userId"))

        assertEquals(
            HomeState(
                searchTerm = "@username",
                searchType = SearchType.User("userId")
            ), sut.state.value
        )
    }

    @Test
    fun `when view model loads, tag type is any`() {
        assertEquals(TagType.ANY, sut.state.value.tagType)
    }

    @Test
    fun `when tag type changed, tag type in state is updated`() {
        sut.handleEvent(HomeEvent.TagTypeChanged(true))

        assertEquals(TagType.ALL, sut.state.value.tagType)
    }

    @Test
    fun `test when map search called with term, term is returned`() {
        val result = sut.mapSearchTermToSearchType("term")

        assertEquals(SearchType.Term("term"), result)
    }

    @Test
    fun `test when map search called with userId, user is returned`() {
        val result = sut.mapSearchTermToSearchType("@userId")

        assertEquals(SearchType.User("userId"), result)
    }

    @Test
    fun `test when map search called with one tag, tag is returned`() {
        val result = sut.mapSearchTermToSearchType("#tag1")

        assertEquals(SearchType.Tag("tag1", TagType.ANY), result)
    }

    @Test
    fun `test when map search called with multiple tags, tag is returned`() {
        val result = sut.mapSearchTermToSearchType("#tag1 #tag2")

        assertEquals(SearchType.Tag("tag1,tag2", TagType.ANY), result)
    }

    @Test
    fun `test when map search called with all tags mode, tag is returned`() {
        sut.handleEvent(HomeEvent.TagTypeChanged(true))
        val result = sut.mapSearchTermToSearchType("#tag1")

        assertEquals(SearchType.Tag("tag1", TagType.ALL), result)
    }

    @Test
    fun `test when map search called with blank value, null is returned`() {
        val result = sut.mapSearchTermToSearchType("")

        assertEquals(null, result)
    }

    @Test
    fun `test when map search called with blank # value, null is returned`() {
        val result = sut.mapSearchTermToSearchType("#")

        assertEquals(null, result)
    }

    @Test
    fun `test when map search called with blank @ value, null is returned`() {
        val result = sut.mapSearchTermToSearchType("@")

        assertEquals(null, result)
    }
}