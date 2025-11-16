package com.oliviermarteaux.localshared.firebase.authentication.ui.screen.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.shared.composables.SharedButton
import com.oliviermarteaux.shared.composables.SharedImage

/**
 * A screen that is displayed when the application is launched.
 *
 * @param modifier The modifier to apply to this screen.
 * @param navigateToLoginScreen A function to call to navigate to the login screen.
 */
@Composable
fun SplashScreen(
    logoDrawableRes: Int,
    splashViewModel: SplashViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navigateToLoginScreen: () -> Unit,
) {
    Column(
        modifier = modifier
    ){
        SharedImage(
            painter = painterResource(id = logoDrawableRes),
        )
        SharedButton(
            onClick = navigateToLoginScreen,
            text = stringResource(R.string.splash_screen_sign_in_with_email)
        )
        SharedButton(
            onClick = splashViewModel::signInWithGoogle,
            text = stringResource(R.string.splash_screen_sign_in_with_google)
        )
    }
}