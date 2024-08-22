package com.janey.photo

import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.PhotoData
import com.janey.photo.network.model.SearchType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FakePhotoRepository @Inject constructor() : PhotoRepository {
    override suspend fun getPhotoById(id: String): Photo = getSamplePhoto()

    private val photoFlow = MutableStateFlow<PhotoData?>(null)
    override suspend fun searchPhotos(searchType: SearchType) {
        when(searchType){
            is SearchType.Term -> photoFlow.update {
                PhotoData(
                    photos = listOf(
                        getSamplePhoto("Owner1"),
                        getSamplePhoto("Owner2"),
                    ),
                    page = 9218,
                    pages = 7801,
                    perPage = 7284,
                )
            }
            is SearchType.User -> photoFlow.update {
                PhotoData(
                    photos = listOf(
                        getSamplePhoto(),
                        getSamplePhoto(),
                    ),
                    page = 9218,
                    pages = 7801,
                    perPage = 7284,
                )
            }
            else -> photoFlow.update {
                PhotoData(
                    photos = listOf(getSamplePhoto()),
                    page = 9218,
                    pages = 7801,
                    perPage = 7284,
                )
            }
        }
    }

    override fun subscribe(): Flow<PhotoData?> = photoFlow.asStateFlow()

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