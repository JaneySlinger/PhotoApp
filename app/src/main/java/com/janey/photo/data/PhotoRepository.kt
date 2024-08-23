package com.janey.photo.data

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import com.janey.photo.network.FlickrApiService
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.SearchType
import javax.inject.Inject

interface PhotoRepository {
    fun photoPagingSource(searchType: SearchType): PagingSource<Int, Photo>
    fun getPhotoById(id: String): Photo?
    fun storePhoto(photo: Photo)
    fun clearCache()
}

class PhotoRepositoryImpl @Inject constructor(
    private val flickrApiService: FlickrApiService,
) : PhotoRepository {
    @VisibleForTesting
    val cachedPhotos: MutableList<Photo> = mutableListOf()
    override fun getPhotoById(id: String): Photo? {
        return cachedPhotos.firstOrNull { photo -> photo.id == id }
    }

    override fun photoPagingSource(searchType: SearchType) =
        PhotoPagingSource(searchType, flickrApiService)

    override fun storePhoto(photo: Photo) {
        cachedPhotos.add(photo)
    }

    override fun clearCache() {
        cachedPhotos.clear()
    }

}