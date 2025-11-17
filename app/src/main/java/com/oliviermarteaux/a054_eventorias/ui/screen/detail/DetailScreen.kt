package com.oliviermarteaux.a054_eventorias.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.composables.SharedGoogleMap
import com.oliviermarteaux.localshared.composables.SharedScaffold
import com.oliviermarteaux.localshared.extensions.toLocalDate
import com.oliviermarteaux.localshared.extensions.toLocalTime
import com.oliviermarteaux.localshared.extensions.toLocalTimeString
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedAsyncImage
import com.oliviermarteaux.shared.composables.SharedIcon
import com.oliviermarteaux.shared.extensions.toHumanDate
import com.oliviermarteaux.shared.extensions.toLocalDateString

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                SharedAsyncImage(
                    photoUri = post.photoUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SharedIcon(
                                icon = IconSource.VectorIcon(Icons.Default.CalendarToday),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = post.date.toLocalDate().toLocalDateString(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SharedIcon(
                                icon = IconSource.VectorIcon(Icons.Default.Schedule),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = post.time.toLocalTime().toLocalTimeString(),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.martyna_siddeswara),
                        contentDescription = "Organizer",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
//                                text = "123 Rue de l'Art, Quartier des Galeries, Paris, 75003, France",
                                text = "${post.address.street}, ${post.address.district},"
                                        +"${post.address.city}, ${post.address.zipCode}, "
                                        + post.address.country,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
//                        Image(
//                            painter = painterResource(id = R.drawable.placeholder),
//                            contentDescription = "Map preview",
//                            modifier = Modifier
//                                .size(80.dp)
//                                .clip(MaterialTheme.shapes.small)
//                        )
                        SharedGoogleMap(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun EventDetailsScreenPreview() {
//    DetailScreen(onBackClick = {})
//}
