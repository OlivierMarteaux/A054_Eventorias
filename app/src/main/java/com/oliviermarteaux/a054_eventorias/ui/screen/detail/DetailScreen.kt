package com.oliviermarteaux.a054_eventorias.ui.screen.detail

import android.R.attr.contentDescription
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.firebase.BuildConfig
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.apikeys.GOOGLE_MAPS_API_KEY
import com.oliviermarteaux.localshared.composables.SharedGoogleMapFromCoords
import com.oliviermarteaux.localshared.composables.SharedScaffold
import com.oliviermarteaux.localshared.composables.StaticGoogleMap
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedAsyncImage
import com.oliviermarteaux.localshared.composables.SharedIcon
import com.oliviermarteaux.localshared.ui.theme.SharedPadding
import com.oliviermarteaux.shared.ui.theme.SharedSize

@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    with(detailViewModel) {
        SharedScaffold(
            title = post.title,
            onBackClick = onBackClick
        ) { paddingValues ->
            DetailBody(
                paddingValues = paddingValues,
                post = post,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = SharedPadding.xl)
            )
        }
    }
}

@Composable
fun DetailBody(
    paddingValues: PaddingValues,
    post: Post,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        DetailImageCard(post)
        Spacer(modifier = Modifier.height(SharedPadding.xl))

        DetailScheduleAndAuthorCard(post)
        Spacer(modifier = Modifier.height(SharedPadding.xl))

        DetailDescriptionCard(post)
        Spacer(modifier = Modifier.height(SharedPadding.xxl))

        DetailAddressCard(post)
    }
}

@Composable
fun DetailImageCard(
    post: Post
) {
    SharedAsyncImage(
        photoUri = post.photoUrl,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DetailScheduleAndAuthorCard(post: Post) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().height(60.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                SharedIcon(
                    icon = IconSource.VectorIcon(Icons.Default.CalendarToday),
                    modifier = Modifier.size(SharedSize.extraSmall)
                )
                Text(
                    text = post.localeDateString,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                SharedIcon(
                    icon = IconSource.VectorIcon(Icons.Default.Schedule),
                    modifier = Modifier.size(SharedSize.extraSmall)
                )
                Text(
                    text = post.localeTimeString,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        SharedAsyncImage(
            photoUri = post.author?.photoUrl,
            modifier = Modifier.clip(CircleShape).fillMaxHeight()
        )
    }
}

@Composable
fun DetailDescriptionCard(post: Post) {
    Text(
        text = post.description,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun DetailAddressCard(post: Post) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = post.address.fullAddress,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(3/5f)
        )
        Spacer(Modifier.width(SharedPadding.xl))
        StaticGoogleMap(
            address = post.address.fullAddress,
            zoom = 16,
            mapApiKey = GOOGLE_MAPS_API_KEY,
            modifier = Modifier
                .weight(2/5f)
                .aspectRatio(149/72f)
                .clip(MaterialTheme.shapes.medium)
        )
//            SharedGoogleMapFromCoords(
//                modifier = Modifier
//                    .size(200.dp)
//                    .clip(MaterialTheme.shapes.small)
//            )
//            SharedGoogleMapFromString(
//                address = post.address.fullAddress ,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(MaterialTheme.shapes.small)
//            )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun EventDetailsScreenPreview() {
//    DetailScreen(onBackClick = {})
//}
