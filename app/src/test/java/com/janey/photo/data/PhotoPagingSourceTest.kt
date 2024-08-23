package com.janey.photo.data

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.janey.photo.FakeFlickrApiServiceImpl
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.SearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoPagingSourceTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test paging loads`() = runTest(testDispatcher) {
        val pagingSource =
            PhotoPagingSource(searchType = SearchType.Term("Yorkshire"), FakeFlickrApiServiceImpl())

        val pager = TestPager(config = PagingConfig(pageSize = 20), pagingSource = pagingSource)

        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertEquals(
            listOf(
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
            ), result.data
        )
    }
}