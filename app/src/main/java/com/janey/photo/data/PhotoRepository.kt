package com.janey.photo.data

import android.util.Log
import com.janey.photo.network.FlickrApi
import com.janey.photo.network.model.PhotoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface PhotoRepository {
    suspend fun searchPhotos(searchTerm: String)
    suspend fun subscribe(): Flow<PhotoData?>
}

class PhotoRepositoryImpl @Inject constructor() : PhotoRepository {
    private val photoFlow = MutableStateFlow<PhotoData?>(null)
    override suspend fun searchPhotos(searchTerm: String) {
        try {
            photoFlow.update { FlickrApi.retrofitService.getImageBySearch(searchText = searchTerm).photos }
        } catch (e: Exception) {
            e.message?.let { Log.d("Janey", it) }
        }
    }

    override suspend fun subscribe(): StateFlow<PhotoData?> = photoFlow.asStateFlow()

}