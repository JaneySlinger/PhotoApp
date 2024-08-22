package com.janey.photo.network

import com.janey.photo.BuildConfig
import com.janey.photo.network.model.FlickrSearchResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val flickrImagesRetrofit = Retrofit.Builder()
    .baseUrl("https://${BuildConfig.FLICKR_BASE_URL}")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface FlickrApiService {
    @GET(".")
    suspend fun getImageBySearch(
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey : String = BuildConfig.FLICKR_API_KEY,
        @Query("format") format: String = "json",
        @Query("safe_search") safeSearch: Int = 1,
        @Query("text") searchText : String? = null,
        @Query("user_id") userId : String? = null,
        @Query("tags") tags : String? = null,
        @Query("tag_mode") tagMode : String? = null,
        @Query("per_page") perPage: Int = 5,
        @Query("extras") extras : String = "tags,url_m,owner_name,icon_server,description,date_taken",
    ): FlickrSearchResponse
}

object FlickrApi {
    val retrofitService: FlickrApiService by lazy {
        flickrImagesRetrofit.create(
            FlickrApiService::class.java
        )
    }
}

