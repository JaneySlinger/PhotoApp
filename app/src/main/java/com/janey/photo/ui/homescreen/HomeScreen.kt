package com.janey.photo.ui.homescreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.janey.photo.R
import com.janey.photo.network.model.Description
import com.janey.photo.network.model.Photo
import com.janey.photo.ui.theme.PhotoTheme
import com.janey.photo.ui.theme.Typography
import com.janey.photo.utils.formatTags

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = viewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    HomeScreenContent(
        modifier = modifier, photos = state.value.photos, searchTerm = state.value.searchTerm,
        onSearchTermUpdated = {
            viewModel.handleEvent(
                HomeEvent.SearchFieldUpdated(
                    it
                )
            )
        },
        onSearchClicked = { viewModel.handleEvent(HomeEvent.SearchClicked) }
    )
}

@Composable
fun HomeScreenContent(
    photos: List<Photo>,
    searchTerm: String,
    onSearchTermUpdated: (String) -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            TextField(
                leadingIcon = { Icon(Icons.Outlined.Search, "") },
                modifier = Modifier.fillMaxWidth(),
                value = searchTerm,
                onValueChange = { onSearchTermUpdated(it) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearchClicked()
                    focusManager.clearFocus()
                }),
            )
            LazyColumn {
                items(photos) { photo ->
                    ImageItem(photo)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun ImageItem(photo: Photo, modifier: Modifier = Modifier) {
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
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(4.dp)),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            fallback = painterResource(id = R.drawable.ic_launcher_background),
        )
        Profile(
            username = photo.ownerName,
            // todo janey move
            profilePictureUrl = "https://farm${photo.iconFarm}.staticflickr.com/${photo.iconServer}/buddyicons/${photo.ownerId}.jpg"
        )
        if(photo.tags.isNotBlank()) {
            Text(text = photo.tags.formatTags(), maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun Profile(
    username: String, profilePictureUrl: String, modifier: Modifier = Modifier
) {
    Row(
        Modifier
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
            photos = listOf(
                Photo(
                    ownerId = "153873640@N02",
                    ownerName = "Ellen Love",
                    iconServer = "1234",
                    iconFarm = 8574,
                    tags = "swjuk ©swjuk uk unitedkingdom gb greatbritain england yorkshire yorkshirecoast northyorkshire harbour redcar paddyshole water sea seaside lobsterpots boats teesside bluesky clouds cloud light sunlight 2024 july2024 summer holidays nikon z50 nikonz50 nikkorzdx1650mm rawnef lightroomclassiccc",
                    photoUrl = "https://live.staticflickr.com/65535/53936023219_558928a4c7_s.jpg",
                    title = "eam",
                    description = Description(contentDescription = "Providing the motive power for the North Yorkshire Moors heritage railway's service over mainline metals to Whitby was class 25 D7628 'Sybilla'."),
                    dateTaken = "2024-08-12 12:50:31"
                ), Photo(
                    ownerId = "153873640@N02",
                    ownerName = "Finn",
                    iconServer = "1234",
                    iconFarm = 8574,
                    tags = "",
                    photoUrl = "https://live.staticflickr.com/65535/53936023219_558928a4c7_s.jpg",
                    title = "eam",
                    description = Description(contentDescription = "Providing the motive power for the North Yorkshire Moors heritage railway's service over mainline metals to Whitby was class 25 D7628 'Sybilla'."),
                    dateTaken = "2024-08-12 12:50:31"
                ), Photo(
                    ownerId = "153873640@N02",
                    ownerName = "Bob",
                    iconServer = "1234",
                    iconFarm = 8574,
                    tags = "yorkshire trees moretags tagfour somemoretags",
                    photoUrl = "https://live.staticflickr.com/65535/53936023219_558928a4c7_s.jpg",
                    title = "eam",
                    description = Description(contentDescription = "Providing the motive power for the North Yorkshire Moors heritage railway's service over mainline metals to Whitby was class 25 D7628 'Sybilla'."),
                    dateTaken = "2024-08-12 12:50:31"
                )
            ), searchTerm = "Yorkshire",
            onSearchTermUpdated = { _ -> },
            onSearchClicked = {}
        )
    }
}
