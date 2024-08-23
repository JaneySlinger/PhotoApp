package com.janey.photo

import com.janey.photo.network.FlickrApiService
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.FlickrSearchResponse
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.PhotoData

class FakeFlickrApiServiceImpl : FlickrApiService {
    override suspend fun getImageBySearch(
        noJsonCallback: Int,
        method: String,
        apiKey: String,
        format: String,
        safeSearch: Int,
        searchText: String?,
        userId: String?,
        tags: String?,
        tagMode: String?,
        page: Int,
        perPage: Int,
        extras: String
    ) = FlickrSearchResponse(
        photoData = PhotoData(
            page = 7208,
            pages = 4233,
            perPage = 6559,
            photos = listOf(
                Photo(
                    id = "graeco",
                    ownerId = "fusce",
                    ownerName = "Kermit Dillard",
                    iconServer = "nonumy",
                    iconFarm = 2071,
                    tags = "pertinax",
                    photoUrl = "https://www.google.com/#q=convenire",
                    title = "quis",
                    description = Description(contentDescription = "recteque"),
                    dateTaken = "pellentesque"
                )
            )
        )
    )
}