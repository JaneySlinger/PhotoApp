package com.janey.photo.di

import com.janey.photo.data.PhotoRepository
import com.janey.photo.data.PhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoModule {
    @Binds
    @Singleton
    abstract fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository
}