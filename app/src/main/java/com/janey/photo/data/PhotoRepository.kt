package com.janey.photo.data

import com.janey.photo.network.FlickrApi
import com.janey.photo.network.model.PhotoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PhotoRepository {
    suspend fun searchPhotos(searchTerm: String): Flow<PhotoData>
}

class PhotoRepositoryImpl @Inject constructor(): PhotoRepository {
    override suspend fun searchPhotos(searchTerm: String): Flow<PhotoData> = flow {
        FlickrApi.retrofitService.getImageBySearch(searchText = searchTerm)
    }
}