package com.janey.photo

import com.janey.photo.data.PhotoRepository
import com.janey.photo.di.PhotoModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PhotoModule::class]
)
abstract class TestPhotoModule {
    @Binds
    @Singleton
    abstract fun bindPhotoRepository(fakePhotoRepository: FakePhotoRepository): PhotoRepository
}