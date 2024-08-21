package com.janey.photo.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Photo
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
            photoRepository.searchPhotos(state.value.searchTerm)

        }
        photoRepository.subscribe().onEach {
            it?.let { update(state.value.copy(photos = it.photos)) }
        }.launchIn(viewModelScope)
    }

    fun handleEvent(event: HomeEvent){
        when(event){
            HomeEvent.SearchClicked -> viewModelScope.launch {
                photoRepository.searchPhotos(state.value.searchTerm)
            }
            is HomeEvent.SearchFieldUpdated -> update(state.value.copy(searchTerm = event.searchTerm))
        }
    }
}

data class HomeState(
    val photos: List<Photo> = listOf(),
    val searchTerm: String = "Yorkshire",
)

sealed class HomeEvent() {
    data class SearchFieldUpdated(val searchTerm: String) : HomeEvent()
    data object SearchClicked : HomeEvent()
}