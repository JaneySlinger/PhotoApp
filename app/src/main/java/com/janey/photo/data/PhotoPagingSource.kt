package com.janey.photo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.janey.photo.network.FlickrApiService
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.SearchType
import java.io.IOException
import javax.inject.Inject

class PhotoPagingSource @Inject constructor(
    private val searchType: SearchType,
    private val flickrApiService: FlickrApiService,
): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPage = params.key ?: 1
            val photoData = when (searchType) {
                is SearchType.Tag -> flickrApiService.getImageBySearch(
                    tags = searchType.tags,
                    tagMode = searchType.tagType.value
                ).photoData

                is SearchType.Term -> flickrApiService.getImageBySearch(searchText = searchType.searchTerm).photoData
                is SearchType.User -> flickrApiService.getImageBySearch(userId = searchType.userId).photoData
            }
            val maxPages = photoData.pages
            val perPage = photoData.perPage

            LoadResult.Page(
                data = photoData.photos,
                prevKey = if(currentPage == 1) null else currentPage - 1,
                nextKey = if(currentPage + perPage <= maxPages) currentPage + 1 else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}