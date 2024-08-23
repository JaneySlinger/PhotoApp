package com.janey.photo.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.janey.photo.data.PhotoRepository
import com.janey.photo.network.model.Photo
import com.janey.photo.network.model.SearchType
import com.janey.photo.network.model.TagType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
) : ViewModel() {
    val state = MutableStateFlow(HomeState())

    @OptIn(ExperimentalCoroutinesApi::class)
    var photos: Flow<PagingData<Photo>> = state.flatMapLatest {
        Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { photoRepository.photoPagingSource(state.value.searchType) }
        ).flow
            .cachedIn(viewModelScope)
    }

    private fun update(newState: HomeState) {
        state.value = newState
    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.SearchClicked -> getPhotos(mapSearchTermToSearchType(state.value.searchTerm))
            is HomeEvent.SearchFieldUpdated -> update(state.value.copy(searchTerm = event.searchTerm))
            is HomeEvent.UserClicked -> {
                update(state.value.copy(searchTerm = "@${event.username}"))
                getPhotos(SearchType.User(event.userId))
            }

            is HomeEvent.ImageClicked -> photoRepository.storePhoto(event.photo)
        }
    }

    private fun getPhotos(searchType: SearchType?) {
        searchType?.let {
            update(state.value.copy(searchType = searchType))
        }
    }

    @VisibleForTesting
    fun mapSearchTermToSearchType(term: String): SearchType? {
        if(term.isBlank()) return null
        // if it's a user search or a tag search it needs a value
        if(term.length == 1 && (term == "@" || term == "#")) return null

        return if (term.first() == '@') {
            SearchType.User(term.drop(1))
        } else if (term.first() == '#') {
            SearchType.Tag(
                tags = term.drop(1).replace(" ", "").split('#').joinToString(","),
                tagType = TagType.ANY
            )
        } else {
            SearchType.Term(term)
        }
    }
}

data class HomeState(
    val searchTerm: String = "Yorkshire",
    val searchType: SearchType = SearchType.Term("Yorkshire")
)

sealed class HomeEvent {
    data class SearchFieldUpdated(val searchTerm: String) : HomeEvent()
    data class UserClicked(val username: String, val userId: String) : HomeEvent()
    data object SearchClicked : HomeEvent()
    data class ImageClicked(val photo: Photo) : HomeEvent()
}