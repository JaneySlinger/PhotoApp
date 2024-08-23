package com.janey.photo.data

import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PhotoRepositoryTest {

    private lateinit var sut: PhotoRepositoryImpl

    @Before
    fun setup() {
        sut = PhotoRepositoryImpl()
    }

    @Test
    fun `given cache contains photo, getPhotoById returns photo`() {
        sut.storePhoto(photo)

        val retrievedPhoto = sut.getPhotoById("1")

        assertEquals(photo, retrievedPhoto)
    }

    @Test
    fun `given cache is empty, getPhotoById returns null`() {
        val retrievedPhoto = sut.getPhotoById("1")

        assertEquals(null, retrievedPhoto)
    }

    @Test
    fun `given cache has other values, getPhotoById for an id not in the cache, returns null`() {
        sut.storePhoto(photo.copy(id = "2"))

        val retrievedPhoto = sut.getPhotoById("1")

        assertEquals(null, retrievedPhoto)
    }

    @Test
    fun `when store a photo, the photo is added to the cache`() {
        sut.storePhoto(photo)

        assertEquals(listOf(photo), sut.cachedPhotos)
    }

    @Test
    fun `given data in the cache, when clearing the cache, the cache is empty`() {
        sut.storePhoto(photo)

        sut.clearCache()

        assertEquals(emptyList<Photo>(), sut.cachedPhotos)
    }

    companion object {
        val photo = Photo(
            id = "1",
            ownerId = "libris",
            ownerName = "Nora Norris",
            iconServer = "tortor",
            iconFarm = 1474,
            tags = "intellegat",
            photoUrl = "https://www.google.com/#q=scripserit",
            title = "vis",
            description = Description(contentDescription = "fuisset"),
            dateTaken = "id"
        )
    }
}