package com.janey.photo.ui.homeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janey.photo.data.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
): ViewModel() {
    init {
        viewModelScope.launch {
            photoRepository.searchPhotos("Yorkshire").collect {
                Log.d("Janey", "photos are: ${it.photos.toString()}")
            }
        }
    }
}