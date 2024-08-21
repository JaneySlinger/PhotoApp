package com.janey.photo

import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.PhotoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakePhotoRepository : PhotoRepository {
    private val photoFlow = MutableStateFlow<PhotoData?>(null)
    override suspend fun searchPhotos(searchTerm: String) {
        photoFlow.update {
            PhotoData(
                photos = listOf(
                    Photo(
                        ownerId = "tortor",
                        ownerName = searchTerm,
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

    override fun subscribe(): Flow<PhotoData?> = photoFlow.asStateFlow()
}