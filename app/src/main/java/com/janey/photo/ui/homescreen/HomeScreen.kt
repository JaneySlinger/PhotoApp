package com.janey.photo.ui.homescreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.janey.photo.R
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.ui.theme.PhotoTheme
import com.janey.photo.ui.theme.Typography
import com.janey.photo.utils.formatProfileUrl
import com.janey.photo.utils.formatTags

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onImageClicked: (String) -> Unit,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val photosPagingItems = viewModel.photos.collectAsLazyPagingItems()
    HomeScreenContent(
        modifier = modifier,
        photosPagingItems = photosPagingItems,
        searchTerm = state.value.searchTerm,
        onImageClicked = onImageClicked,
        onNavigateClicked = {viewModel.handleEvent(HomeEvent.ImageClicked(it))},
        onSearchTermUpdated = {
            viewModel.handleEvent(
                HomeEvent.SearchFieldUpdated(
                    it
                )
            )
        },
        onUserClicked = { userName, userId ->
            viewModel.handleEvent(
                HomeEvent.UserClicked(
                    userName,
                    userId
                )
            )
        },
        onSearchClicked = { viewModel.handleEvent(HomeEvent.SearchClicked) }
    )
}

@Composable
fun HomeScreenContent(
    photosPagingItems: LazyPagingItems<Photo>?,
    searchTerm: String,
    onImageClicked: (String) -> Unit,
    onNavigateClicked: (Photo) -> Unit,
    onSearchTermUpdated: (String) -> Unit,
    onUserClicked: (String, String) -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            TextField(
                leadingIcon = { Icon(Icons.Outlined.Search, "") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("SearchField"),
                value = searchTerm,
                onValueChange = { onSearchTermUpdated(it) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearchClicked()
                    focusManager.clearFocus()
                }),
            )

            photosPagingItems?.let {
                LazyColumn {
                    items(photosPagingItems.itemCount) { index ->
                        ImageItem(
                            photosPagingItems[index]!!,
                            onImageClicked,
                            onNavigateClicked,
                            onUserClicked
                        )
                        HorizontalDivider()
                    }
                    photosPagingItems.apply {
                        when {
                            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                item {
                                    Text("Loading")
                                }
                            }

                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                item {
                                    Text("Error")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageItem(
    photo: Photo,
    onImageClicked: (String) -> Unit,
    onNavigateClicked: (Photo) -> Unit,
    onUserClicked: (String, String) -> Unit,
) {
    Column(Modifier.padding(8.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(photo.photoUrl).crossfade(true)
                .build(),
            contentDescription = photo.description.contentDescription,
            contentScale = ContentScale.Crop,
            onLoading = { },
            onSuccess = { },
            onError = { },
            modifier = Modifier
                .clickable {
                    onNavigateClicked(photo)
                    onImageClicked(photo.id)
                }
                .fillMaxWidth()
                .height(200.dp)
                .testTag("Image")
                .clip(RoundedCornerShape(4.dp)),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            fallback = painterResource(id = R.drawable.ic_launcher_background),
        )
        Profile(
            username = photo.ownerName,
            modifier = Modifier
                .clickable {
                    onUserClicked(photo.ownerName, photo.ownerId)
                },
            profilePictureUrl = formatProfileUrl(photo.iconFarm, photo.iconServer, photo.ownerId)
        )
        if (photo.tags.isNotBlank()) {
            Text(text = photo.tags.formatTags(), maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun Profile(
    username: String,
    profilePictureUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(profilePictureUrl)
                .crossfade(true).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onLoading = { },
            onSuccess = { },
            onError = { Log.d("Janey", it.result.throwable.message.toString()) },
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            fallback = painterResource(id = R.drawable.ic_launcher_background),
        )
        Text(username, style = Typography.bodyLarge)
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    PhotoTheme {
        HomeScreenContent(
            searchTerm = "Yorkshire",
            onSearchTermUpdated = { _ -> },
            onSearchClicked = {},
            onImageClicked = {},
            onUserClicked = { _, _ -> },
            photosPagingItems = null,
            onNavigateClicked = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun ProfilePreview() {
    PhotoTheme {
        Surface {
            Profile(username = "Username", profilePictureUrl = "testUrl")
        }
    }
}

@PreviewLightDark
@Composable
private fun ImageItemPreview() {
    PhotoTheme {
        Surface {
            ImageItem(
                photo = Photo(
                    id = "1",
                    ownerId = "tortor",
                    ownerName = "Owner",
                    iconServer = "omnesque",
                    iconFarm = 4598,
                    tags = "tag1 tag2 tag3",
                    photoUrl = "https://www.google.com/#q=similique",
                    title = "mei",
                    description = Description(contentDescription = "moderatius"),
                    dateTaken = "expetendis"
                ),
                onImageClicked = {},
                onNavigateClicked = {},
                onUserClicked = {_, _ ->},
            )
        }
    }
}
