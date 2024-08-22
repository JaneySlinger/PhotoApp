package com.janey.photo.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
) : ViewModel() {
    val state = MutableStateFlow(HomeState())

    private fun update(newState: HomeState) {
        state.value = newState
    }

    init {
        viewModelScope.launch {
            photoRepository.searchPhotos(SearchType.Term(state.value.searchTerm))

        }
        photoRepository.subscribe().onEach {
            it?.let { update(state.value.copy(photos = it.photos)) }
        }.launchIn(viewModelScope)
    }

    fun handleEvent(event: HomeEvent){
        when(event){
            HomeEvent.SearchClicked -> getPhotos(SearchType.Term(state.value.searchTerm))
            is HomeEvent.SearchFieldUpdated -> update(state.value.copy(searchTerm = event.searchTerm))
            is HomeEvent.UserClicked -> {
                update(state.value.copy(searchTerm = "@${event.username}"))
                getPhotos(SearchType.User(event.userId))
            }
        }
    }

    private fun getPhotos(searchType: SearchType) {
        viewModelScope.launch {
            photoRepository.searchPhotos(searchType)
        }
    }
}

data class HomeState(
    val photos: List<Photo> = listOf(),
    val searchTerm: String = "Yorkshire",
)

sealed class HomeEvent {
    data class SearchFieldUpdated(val searchTerm: String) : HomeEvent()
    data class UserClicked(val username: String, val userId: String) : HomeEvent()
    data object SearchClicked : HomeEvent()
}