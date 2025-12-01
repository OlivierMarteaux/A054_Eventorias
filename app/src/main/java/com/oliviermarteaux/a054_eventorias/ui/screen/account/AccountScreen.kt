package com.oliviermarteaux.a054_eventorias.ui.screen.account

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.a054_eventorias.ui.theme.Red40
import com.oliviermarteaux.a054_eventorias.ui.theme.White
import com.oliviermarteaux.localshared.composables.SharedBottomAppBar
import com.oliviermarteaux.localshared.composables.SharedButton
import com.oliviermarteaux.localshared.composables.SharedScaffold
import com.oliviermarteaux.localshared.composables.SharedTextField
import com.oliviermarteaux.localshared.composables.spacer.SpacerLarge
import com.oliviermarteaux.localshared.composables.spacer.SpacerSmall
import com.oliviermarteaux.localshared.composables.spacer.SpacerXl
import com.oliviermarteaux.localshared.firebase.authentication.domain.model.User
import com.oliviermarteaux.localshared.ui.UiState
import com.oliviermarteaux.localshared.ui.theme.SharedPadding
import com.oliviermarteaux.localshared.ui.theme.ToastPadding
import com.oliviermarteaux.shared.composables.CenteredCircularProgressIndicator
import com.oliviermarteaux.shared.composables.SharedToast

@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel = hiltViewModel(),
    navController: NavController
) {
    with(accountViewModel) {
        SharedScaffold(
            title = "User profile",
            avatarUrl = user.photoUrl,
            topAppBarModifier = Modifier.padding(horizontal = SharedPadding.small),
            bottomBar = { SharedBottomAppBar(navController = navController) }
        ) { paddingValues ->
            Box(){
                AccountScreenBody(
                    user = user,
                    notificationState = notificationState,
                    toggleNotifications = ::toggleNotifications,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = SharedPadding.large)
                )
                if (userUiState is UiState.Loading) { CenteredCircularProgressIndicator() }
                if (userUiState is UiState.Error) {
                    SharedToast(
                        text = stringResource(R.string.an_unknown_error_occurred),
                        bottomPadding = ToastPadding.high
                    )
                }
                if (networkError) SharedToast(
                    text = stringResource(R.string.network_error_check_your_internet_connection),
                    bottomPadding = ToastPadding.medium
                )
                if (authError) SharedToast(
                    text = stringResource(R.string.user_is_disconnected),
                    bottomPadding = ToastPadding.veryHigh
                )
            }
        }
    }
}

@Composable
fun AccountScreenBody(
    user: User,
    modifier: Modifier = Modifier,
    notificationState: Boolean = true,
    toggleNotifications: () -> Unit
){
    Column(
        modifier = modifier
    ) {
        SharedTextField(
            value = user.getComputedFullName(),
            label = stringResource(R.string.name),
            textFieldModifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        SpacerLarge()

        SharedTextField(
            value = user.email,
            label = stringResource(R.string.email),
            textFieldModifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        SpacerXl()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Switch(
                checked = notificationState,
                onCheckedChange = { toggleNotifications() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = Red40,
                )
            )
            SpacerSmall()
            Text(
                text = stringResource(R.string.notifications),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}