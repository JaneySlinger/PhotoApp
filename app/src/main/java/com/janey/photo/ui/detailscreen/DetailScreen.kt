package com.janey.photo.ui.detailscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.janey.photo.R
import com.janey.photo.ui.homescreen.Profile
import com.janey.photo.ui.theme.PhotoTheme
import com.janey.photo.ui.theme.Typography
import com.janey.photo.utils.formatTags

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel = viewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    DetailScreenContent(
        modifier = modifier,
        url = state.value.url,
        description = state.value.description,
        title = state.value.title,
        dateTaken = state.value.dateTaken,
        userName = state.value.userName,
        profileUrl = state.value.profileUrl,
        tags = state.value.tags,
    )
}

@Composable
fun DetailScreenContent(
    url: String,
    description: String,
    title: String,
    dateTaken: String,
    userName: String,
    tags: String,
    profileUrl: String,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(url).crossfade(true)
                    .build(),
                contentDescription = description,
                contentScale = ContentScale.Crop,
                onLoading = { },
                onSuccess = { },
                onError = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                fallback = painterResource(id = R.drawable.ic_launcher_background),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp)
            ) {
                Profile(userName, profileUrl)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(title, style = Typography.titleLarge, textAlign = TextAlign.Center)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Outlined.DateRange, "")
                    Text(dateTaken)
                }
                Text(
                    description,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(top = 4.dp)
                        .fillMaxWidth()
                )
                if(tags.isNotBlank()) {
                    Text(
                        tags.formatTags(),
                        style = Typography.bodyLarge,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
private fun DetailScreenContentPreview() {
    PhotoTheme {
        DetailScreenContent(
            url = "https://live.staticflickr.com/65535/53936023219_558928a4c7_s.jpg",
            description = "A really long description that goes off the page. Providing the motive power for the North Yorkshire Moors heritage railway's service over mainline metals to Whitby was class 25 D7628 'Sybilla'.",
            title = "A long title for the image",
            dateTaken = "2024-08-12 12:50:31",
            userName = "Michel Lynn is a really long name",
            tags = "tag1 tag2 tag3",
            profileUrl = "http://www.bing.com/search?q=hendrerit",
        )
    }
}

