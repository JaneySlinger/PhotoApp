package com.janey.photo.ui

import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.SearchType
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
}