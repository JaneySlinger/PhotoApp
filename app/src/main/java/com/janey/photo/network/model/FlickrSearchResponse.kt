package com.janey.photo.network.model

import com.squareup.moshi.Json

data class FlickrSearchResponse(
    @Json(name = "photos")
    val photoData: PhotoData
)

data class PhotoData(
    val page: Int,
    val pages: Int,
    @Json(name="perpage")
    val perPage: Int,
    @Json(name = "photo")
    val photos: List<Photo>,
)

data class Photo(
    val id: String,
    @Json(name = "owner")
    val ownerId: String,
    @Json(name = "ownername")
    val ownerName: String,
    @Json(name = "iconserver")
    val iconServer: String,
    @Json(name = "iconfarm")
    val iconFarm: Int,
    val tags: String,
    @Json(name="url_m")
    val photoUrl: String,
    val title: String,
    val description: Description,
    @Json(name="datetaken")
    val dateTaken: String,
)

data class Description(
    @Json(name = "_content")
    val contentDescription: String,
)

