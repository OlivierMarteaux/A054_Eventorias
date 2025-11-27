package com.oliviermarteaux.localshared.firebase.authentication.ui.screen.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.composables.SharedButton
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedImage
import com.oliviermarteaux.shared.composables.SharedScaffold

/**
 * A screen that is displayed when the application is launched.
 *
 * @param modifier The modifier to apply to this screen.
 * @param navigateToLoginScreen A function to call to navigate to the login screen.
 */
@Composable
fun SplashScreen(
    logoDrawableRes: Int,
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    with(splashViewModel) {
        SharedScaffold(
            modifier = modifier,
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 85.dp),
            ) {
                SharedImage(
                    painter = painterResource(id = logoDrawableRes),
                    modifier = Modifier
                        .height( 120.dp)
                        .weight(1f)
                )
                Spacer(Modifier.height(24.dp))
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    SharedButton(
                        onClick = { signInWithGoogle(navigateToHomeScreen) },
                        text = stringResource(R.string.sign_in_with_Google),
                        textColor = Color.Black,
                        icon = IconSource.PainterIcon(painterResource(id = R.drawable.ic_google_logo)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = MaterialTheme.shapes.extraSmall,
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.height(24.dp))

                    SharedButton(
                        onClick = navigateToLoginScreen,
                        text = stringResource(R.string.sign_in_with_email),
                        textColor = Color.White,
                        icon = IconSource.PainterIcon(painterResource(id = R.drawable.ic_mail_white_no_outline)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                        shape = MaterialTheme.shapes.extraSmall,
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}