package com.janey.photo.data

import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.SearchType
import javax.inject.Inject

interface PhotoRepository {
    fun photoPagingSource(searchType: SearchType): PhotoPagingSource
    suspend fun getPhotoById(id: String): Photo?
    fun storePhoto(photo: Photo)
    fun clearCache()
}

class PhotoRepositoryImpl @Inject constructor() : PhotoRepository {
    private val cachedPhotos: MutableList<Photo> = mutableListOf()
    override suspend fun getPhotoById(id: String): Photo? {
        return cachedPhotos.firstOrNull { photo -> photo.id == id }
    }

    override fun photoPagingSource(searchType: SearchType) = PhotoPagingSource(searchType)

    override fun storePhoto(photo: Photo) {
        cachedPhotos.add(photo)
    }

    override fun clearCache() {
        cachedPhotos.clear()
    }

}