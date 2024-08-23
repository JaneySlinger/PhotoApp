package com.janey.photo.network

import com.janey.photo.BuildConfig
import com.janey.photo.network.model.FlickrSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
    @GET(".")
    suspend fun getImageBySearch(
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = BuildConfig.FLICKR_API_KEY,
        @Query("format") format: String = "json",
        @Query("safe_search") safeSearch: Int = 1,
        @Query("text") searchText: String? = null,
        @Query("user_id") userId: String? = null,
        @Query("tags") tags: String? = null,
        @Query("tag_mode") tagMode: String? = null,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 5,
        @Query("extras") extras: String = "tags,url_m,owner_name,icon_server,description,date_taken",
    ): FlickrSearchResponse
}

