package com.oliviermarteaux.a054_eventorias.ui.screen.add

import android.R.attr.description
import android.R.attr.onClick
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.composables.SharedScaffold
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.localshared.composables.SharedOutlinedTextField
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedCardAsyncImage
import com.oliviermarteaux.shared.composables.SharedIconButton
import com.oliviermarteaux.shared.composables.sharedImagePicker
import com.oliviermarteaux.shared.ui.theme.SharedPadding

@Composable
fun AddScreen(
    addViewModel: AddViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onValidateClick: () -> Unit = {}
) {
    with(addViewModel) {
        SharedScaffold(
            title = stringResource(R.string.creation_of_an_event),
            onBackClick = navigateBack
        ) { paddingValues ->
            AddScreenBody(
                post = post,
                updatePostTitle = ::updatePostTitle,
                updatePostDescription = ::updatePostDescription,
                updatePostDate = ::updatePostDate,
                updatePostTime = ::updatePostTime,
                updatePostAddress = ::updatePostAddress,
                updatePostPhoto = ::updatePostPhoto,
                paddingValues = paddingValues,
                onValidateClick = {}
            )
        }
    }
}

@Composable
fun AddScreenBody(
    post: Post,
    updatePostTitle: (String) -> Unit,
    updatePostDescription: (String) -> Unit,
    updatePostDate: (String) -> Unit,
    updatePostTime: (String) -> Unit,
    updatePostAddress: (String) -> Unit,
    updatePostPhoto: (String) -> Unit,
    paddingValues: PaddingValues,
    onValidateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddScreenTextForm(
            post = post,
            updatePostTitle = updatePostTitle,
            updatePostDescription = updatePostDescription,
            updatePostDate = updatePostDate,
            updatePostTime = updatePostTime,
            updatePostAddress = updatePostAddress,
        )

        AddScreenPhotoPickButtonsCard(updatePostPhoto)

        post.photoUrl?.let { AddScreenImagePreview(it) }

        Spacer(modifier = Modifier.weight(1f))

        AddScreenSaveButton(
            onValidateClick = onValidateClick
        )
    }
}

@Composable
fun AddScreenTextForm(
    post:Post,
    updatePostTitle: (String) -> Unit,
    updatePostDescription: (String) -> Unit,
    updatePostDate: (String) -> Unit,
    updatePostTime: (String) -> Unit,
    updatePostAddress: (String) -> Unit,
){
    with(post) {
        SharedOutlinedTextField(
            value = title,
            onValueChange = { updatePostTitle(it) },
            label = stringResource(R.string.new_event),
            modifier = Modifier.fillMaxWidth(),
            isError = title.isEmpty(),
            errorText = stringResource(R.string.please_enter_a_title)
        )
        Spacer(modifier = Modifier.height(16.dp))
        SharedOutlinedTextField(
            value = description,
            onValueChange = { updatePostDescription(it) },
            label = stringResource(R.string.tap_here_to_enter_your_description),
            modifier = Modifier.fillMaxWidth(),
            isError = description.isEmpty(),
            errorText = stringResource(R.string.please_enter_a_description)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SharedOutlinedTextField(
                value = localeDateString,
                onValueChange = { updatePostDate(it) },
                label = stringResource(R.string.date),
                placeholder = stringResource(R.string.mm_dd_yyyy),
                modifier = Modifier.weight(1f),
                isError = localeDateString.isEmpty(),
                errorText = "Please enter a date"
            )
            SharedOutlinedTextField(
                value = localeTimeString,
                onValueChange = { updatePostTime(it) },
                label = stringResource(R.string.time),
                placeholder = stringResource(R.string.hh_mm),
                modifier = Modifier.weight(1f),
                isError = localeTimeString.isEmpty(),
                errorText = "Please enter a time"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SharedOutlinedTextField(
            value = address.street,
            onValueChange = { updatePostAddress(it) },
            label = stringResource(R.string.address),
            placeholder = stringResource(R.string.enter_full_address),
            modifier = Modifier.fillMaxWidth(),
            isError = address.street.isEmpty(),
            errorText = "Please enter an address"
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun AddScreenPhotoPickButtonsCard(
    updatePostPhoto: (String) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CameraPhotoPickButton({})

        LocalePhotoPickButton(updatePostPhoto)
    }
}

@Composable
fun LocalePhotoPickButton(onClick: (String) -> Unit) {
    // Get the ImagePicker launcher
    val imagePickerLauncher = sharedImagePicker { onClick(it.toString()) }
    SharedIconButton(
        icon = IconSource.VectorIcon(Icons.Default.AttachFile),
        tint = Color.White,
        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Red)
    ) { imagePickerLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) }
    IconButton(
        onClick = { /* Handle attachment click */ },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Red
        )
    ) {
        Icon(
            Icons.Default.AttachFile,
            contentDescription = stringResource(R.string.add_a_photo),
            tint = Color.White
        )
    }
}

@Composable
fun CameraPhotoPickButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    ) {
        Icon(Icons.Default.CameraAlt, contentDescription = stringResource(R.string.take_a_photo))
    }
}

@Composable
fun AddScreenImagePreview(photoUrl: String){
    SharedCardAsyncImage(
        photoUri = photoUrl,
        imageModifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 4 / 3f),
    )
}

@Composable
fun AddScreenSaveButton(
    onValidateClick: () -> Unit
){
    Button(
        onClick = onValidateClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text(stringResource(R.string.validate), color = Color.White)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddScreenPreview() {
//    AddScreen(onBackClick = {}, onValidateClick = {})
//}