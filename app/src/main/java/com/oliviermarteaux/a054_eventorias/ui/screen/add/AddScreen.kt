package com.oliviermarteaux.a054_eventorias.ui.screen.add

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
import com.oliviermarteaux.localshared.extensions.toLocalDate
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.localshared.composables.SharedOutlinedTextField
import com.oliviermarteaux.localshared.extensions.toLocalTime
import com.oliviermarteaux.localshared.extensions.toLocalTimeString
import com.oliviermarteaux.shared.extensions.toLocalDateString

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
            AddBody(
                post = post,
                onTitleChange = ::onTitleChange,
                onDescriptionChange = ::onDescriptionChange,
                onDateChange = ::onDateChange,
                onTimeChange = ::onTimeChange,
                onAddressChange = ::onAddressChange,
                paddingValues = paddingValues,
                onValidateClick = {}
            )
        }
    }
}

@Composable
fun AddBody(
    post: Post,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onDateChange: (String) -> Unit = {},
    onTimeChange: (String) -> Unit = {},
    onAddressChange: (String) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(),
    onValidateClick: () -> Unit
) {
    with(post) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SharedOutlinedTextField(
                value = title,
                onValueChange = { onTitleChange(it) },
                label = stringResource(R.string.new_event),
                modifier = Modifier.fillMaxWidth(),
                isError = title.isEmpty(),
                errorText = stringResource(R.string.please_enter_a_title)
            )

            Spacer(modifier = Modifier.height(16.dp))

            SharedOutlinedTextField(
                value = description,
                onValueChange = { onDescriptionChange(it) },
                label = stringResource(R.string.tap_here_to_enter_your_description),
                modifier = Modifier.fillMaxWidth(),
                isError =  description.isEmpty(),
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
                    modifier = Modifier.weight(1f),
                    isError = localeDateString.isEmpty(),
                    errorText = "Please enter a date"
                )
                SharedOutlinedTextField(
                    value = localeTimeString,
                    onValueChange = { onTimeChange(it) },
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
                onValueChange = { onAddressChange(it) },
                label = stringResource(R.string.address),
                placeholder = stringResource(R.string.enter_full_address),
                modifier = Modifier.fillMaxWidth(),
                isError = address.street.isEmpty(),
                errorText = "Please enter an address"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(
                    onClick = { /* Handle camera click */ },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = stringResource(R.string.take_a_photo))
                }
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

            Spacer(modifier = Modifier.weight(1f))

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
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddScreenPreview() {
//    AddScreen(onBackClick = {}, onValidateClick = {})
//}