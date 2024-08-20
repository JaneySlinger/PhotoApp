package com.janey.photo.ui.homeScreen

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.janey.photo.R
import com.janey.photo.ui.theme.PhotoTheme
import com.janey.photo.ui.theme.Typography

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel()
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            TextField(
                leadingIcon = { Icon(Icons.Outlined.Search, "") },
                modifier = Modifier.fillMaxWidth(),
                value = "Yorkshire",
                onValueChange = {}
            )
            LazyColumn() {
                items(5) { item ->
                    ImageItem()
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun ImageItem(modifier: Modifier = Modifier) {
    Column(Modifier.padding(8.dp)) {
        // TODO janey add content description
        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(4.dp)),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
        Profile()
        // TODO janey only display
        Text(text = "#yorkshire #trees #moretags #tagfour #somemoretags")
    }
}

@Composable
fun Profile(modifier: Modifier = Modifier) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            // TODO janey add content description
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
        Text("JaneySlinger", style = Typography.bodyLarge)
    }
}

@PreviewLightDark
@Composable
private fun HomeScreenPreview() {
    PhotoTheme {
        HomeScreen()
    }
}