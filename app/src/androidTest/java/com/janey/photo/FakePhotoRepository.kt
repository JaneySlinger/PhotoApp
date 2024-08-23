package com.janey.photo

import androidx.paging.PagingSource
import androidx.paging.testing.asPagingSourceFactory
import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.SearchType
import javax.inject.Inject

class FakePhotoRepository @Inject constructor() : PhotoRepository {
    private val photos = listOf(
        getSamplePhoto("Owner1"),
        getSamplePhoto("Owner2"),
    )

    private val userPhotos = listOf(
        getSamplePhoto("Owner1"),
        getSamplePhoto("Owner1"),
    )

    override suspend fun getPhotoById(id: String): Photo = getSamplePhoto()

    private val termPagingSourceFactory = photos.asPagingSourceFactory()
    private val userPagingSourceFactory = userPhotos.asPagingSourceFactory()

    override fun photoPagingSource(searchType: SearchType): PagingSource<Int, Photo> = when(searchType) {
        is SearchType.Tag -> termPagingSourceFactory()
        is SearchType.Term -> termPagingSourceFactory()
        is SearchType.User -> userPagingSourceFactory()
    }

    override fun storePhoto(photo: Photo) {}

    override fun clearCache() {}

    private fun getSamplePhoto(ownerName: String = "Owner1") = Photo(
        id = "1",
        ownerId = "ownerId",
        ownerName = ownerName,
        iconServer = "server",
        iconFarm = 4598,
        tags = "tag1 tag2 tag3",
        photoUrl = "testUrl.jpg",
        title = "title",
        description = Description(contentDescription = "description"),
        dateTaken = "2021-11-26 16:34:48"
    )
}