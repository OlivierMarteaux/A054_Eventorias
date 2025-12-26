package com.oliviermarteaux.a054_eventorias.ui.screen.account

import android.R.attr.contentDescription
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.a054_eventorias.ui.theme.Red40
import com.oliviermarteaux.a054_eventorias.ui.theme.White
import com.oliviermarteaux.localshared.composables.SharedBottomAppBar
import com.oliviermarteaux.localshared.composables.SharedScaffold
import com.oliviermarteaux.localshared.composables.SharedFilledTextField
import com.oliviermarteaux.localshared.composables.extensions.cdButtonSemantics
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
    val cdProfileScreen =
        stringResource(R.string.profile_screen_here_are_displayed_your_data_and_notifications_settings)
    with(accountViewModel) {
        SharedScaffold(
            title = stringResource(R.string.user_profile),
            screenContentDescription = cdProfileScreen ,
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
                        .testTag("Profile Screen")
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
        val cdStateDescription = stringResource(R.string.not_editable)
//        val cdUserName =
//            stringResource(R.string.currently_registered_name_is, user.getComputedFullName())
        SharedFilledTextField(
            value = user.getComputedFullName(),
            label = stringResource(R.string.name),
//            contentDescription = cdUserName,
            textFieldModifier = Modifier
                .semantics {
//                    stateDescription = cdStateDescription
                    this.contentDescription = cdStateDescription
                }
                .fillMaxWidth(),
            readOnly = true
        )
        SpacerLarge()

//        val cdUserEmail = stringResource(R.string.currently_registered_email_is, user.email)
        SharedFilledTextField(
            value = user.email,
            label = stringResource(R.string.email),
//            contentDescription = cdUserEmail,
            textFieldModifier = Modifier
                .semantics {
//                    stateDescription = cdStateDescription
                    this.contentDescription = cdStateDescription
                }
                .fillMaxWidth(),
            readOnly = true
        )
        SpacerXl()

        val cdNotifications =
            if (notificationState) stringResource(R.string.notification_are_enabled_double_tap_to_disable_it)
            else stringResource(R.string.notification_are_disabled_double_tap_to_enable_it)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Switch(
                checked = notificationState,
                onCheckedChange = { toggleNotifications() },
                modifier = Modifier.cdButtonSemantics(cdNotifications),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = White,
                    checkedTrackColor = Red40,
                )
            )
            SpacerSmall()
            Text(
                text = stringResource(R.string.notifications),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics { hideFromAccessibility() }
            )
        }
    }
}