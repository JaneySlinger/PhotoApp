package com.janey.photo.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
): ViewModel() {
    val state = MutableStateFlow(HomeState())

    private fun update(newState: HomeState) {
        state.value = newState
    }
    init {
        viewModelScope.launch {
            photoRepository.searchPhotos(state.value.searchTerm)
            photoRepository.subscribe().collect {
                it?.let {  update(state.value.copy(photos = it.photos)) }
            }
        }
    }
}

data class HomeState(
    val photos: List<Photo> = listOf(),
    val searchTerm: String = "Yorkshire",
)