package com.janey.photo.ui.detailscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.photo.data.PhotoRepository
import com.janey.photo.utils.formatProfileUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository,
) : ViewModel() {
    private val id: String = checkNotNull(savedStateHandle["id"])
    val state = MutableStateFlow(DetailState())

    private fun update(newState: DetailState) {
        state.value = newState
    }

    init {
        viewModelScope.launch {
            val photo = photoRepository.getPhotoById(id)
            update(state.value.copy(
                url = photo.photoUrl,
                description = photo.description.contentDescription,
                title = photo.title,
                dateTaken = photo.dateTaken,
                userName = photo.ownerName,
                profileUrl = formatProfileUrl(photo.iconFarm, photo.iconServer, photo.ownerId),
                tags = photo.tags,
            ))
        }
    }
}

data class DetailState(
    val url: String = "",
    val description: String = "",
    val title: String = "",
    val dateTaken: String = "", //TODO swap to date
    val userName : String = "",
    val profileUrl: String = "",
    val tags: String = "",
)