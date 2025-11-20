package com.oliviermarteaux.a054_eventorias.ui.screen.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.composables.SharedOutlinedTextField
import com.oliviermarteaux.localshared.composables.SharedScaffold
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedCardAsyncImage
import com.oliviermarteaux.shared.composables.SharedIconButton
import com.oliviermarteaux.shared.composables.sharedImagePicker
import java.util.Calendar
import java.util.Date

@Composable
fun AddScreen(
    addViewModel: AddViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToCamera: ((String) -> Unit) -> Unit
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
                navigateToCamera = navigateToCamera,
                addPost = { addPost(navigateBack) },
                navigateBack = navigateBack,
                paddingValues = paddingValues,
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
    navigateToCamera: ((String) -> Unit) -> Unit,
    addPost: () -> Unit,
    navigateBack: () -> Unit,
    paddingValues: PaddingValues,
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

        AddScreenPhotoPickButtonsCard(
            updatePostPhoto = updatePostPhoto,
            navigateToCamera = navigateToCamera
        )

        post.photoUrl?.let { AddScreenImagePreview(it) }

        Spacer(modifier = Modifier.weight(1f))

        AddScreenSaveButton(
            onClick = addPost
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
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Date Picker
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            updatePostDate("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
        }, year, month, day
    )

    // Time Picker
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            updatePostTime("$selectedHour:$selectedMinute")
        }, hour, minute, true
    )

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
                onValueChange = { },
                label = stringResource(R.string.date),
                placeholder = stringResource(R.string.mm_dd_yyyy),
                modifier = Modifier
                    .weight(1f)
                    .clickable { datePickerDialog.show() },
                isError = localeDateString.isEmpty(),
                errorText = "Please enter a date",
                enabled = false
            )
            SharedOutlinedTextField(
                value = localeTimeString,
                onValueChange = { },
                label = stringResource(R.string.time),
                placeholder = stringResource(R.string.hh_mm),
                modifier = Modifier
                    .weight(1f)
                    .clickable { timePickerDialog.show() },
                isError = localeTimeString.isEmpty(),
                errorText = "Please enter a time",
                enabled = false
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
    updatePostPhoto: (String) -> Unit,
    navigateToCamera: ((String) -> Unit) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CameraPhotoPickButton { navigateToCamera(updatePostPhoto) }

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
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text(stringResource(R.string.validate), color = Color.White)
    }
}
