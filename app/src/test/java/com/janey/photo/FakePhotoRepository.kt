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

class FakePhotoRepository : PhotoRepository {
    override suspend fun getPhotoById(id: String): Photo = Photo(
        id = "1",
        ownerId = "tortor",
        ownerName = "Owner",
        iconServer = "omnesque",
        iconFarm = 4598,
        tags = "senectus",
        photoUrl = "https://www.google.com/#q=similique",
        title = "mei",
        description = Description(contentDescription = "moderatius"),
        dateTaken = "expetendis"
    )

    private val photoFlow = MutableStateFlow<PhotoData?>(null)
    override suspend fun searchPhotos(searchType: SearchType) {
        when(searchType){
            is SearchType.Term -> photoFlow.update {
                PhotoData(
                    photos = listOf(
                        Photo(
                            id = "1",
                            ownerId = "tortor",
                            ownerName = searchType.searchTerm,
                            iconServer = "omnesque",
                            iconFarm = 4598,
                            tags = "senectus",
                            photoUrl = "https://www.google.com/#q=similique",
                            title = "mei",
                            description = Description(contentDescription = "moderatius"),
                            dateTaken = "expetendis"
                        )
                    ),
                    page = 9218,
                    pages = 7801,
                    perPage = 7284,
                )
            }
            else -> photoFlow.update {
                PhotoData(
                    photos = listOf(
                        Photo(
                            id = "1",
                            ownerId = "tortor",
                            ownerName = "owner",
                            iconServer = "omnesque",
                            iconFarm = 4598,
                            tags = "senectus",
                            photoUrl = "https://www.google.com/#q=similique",
                            title = "mei",
                            description = Description(contentDescription = "moderatius"),
                            dateTaken = "expetendis"
                        )
                    ),
                    page = 9218,
                    pages = 7801,
                    perPage = 7284,
                )
            }
        }
    }

    override fun subscribe(): Flow<PhotoData?> = photoFlow.asStateFlow()
}