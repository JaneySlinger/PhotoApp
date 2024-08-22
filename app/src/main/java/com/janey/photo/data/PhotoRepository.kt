package com.janey.photo.data

import android.util.Log
import com.janey.photo.network.FlickrApi
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.PhotoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface PhotoRepository {
    suspend fun searchPhotos(searchTerm: String)
    suspend fun getPhotoById(id: String): Photo
    fun subscribe(): Flow<PhotoData?>
}

class PhotoRepositoryImpl @Inject constructor() : PhotoRepository {
    private val photoFlow = MutableStateFlow<PhotoData?>(null)
    private val cachedPhotos: MutableList<Photo> = mutableListOf()
    override suspend fun getPhotoById(id: String): Photo {
        return cachedPhotos.firstOrNull { photo -> photo.id == id } ?: TODO()
    }

    override suspend fun searchPhotos(searchTerm: String) {
        try {
            val photoData = FlickrApi.retrofitService.getImageBySearch(searchText = searchTerm).photoData
            cachedPhotos.addAll(photoData.photos)
            Log.d("Janey", "Cache size: ${cachedPhotos.size}")
            photoFlow.update { photoData }
        } catch (e: Exception) {
            e.message?.let { Log.d("Janey", it) }
        }
    }

    override fun subscribe(): StateFlow<PhotoData?> = photoFlow.asStateFlow()

    // TODO clear cache method

}