package com.oliviermarteaux.localshared.firebase.authentication.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oliviermarteaux.localshared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.ui.theme.SharedPadding
import com.oliviermarteaux.shared.composables.IconScaffold
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedButton
import com.oliviermarteaux.shared.composables.SharedOutlinedEmail
import com.oliviermarteaux.shared.composables.SharedOutlinedPassword
import com.oliviermarteaux.shared.composables.SharedOutlinedTextField
import com.oliviermarteaux.shared.composables.SharedScaffold
import com.oliviermarteaux.shared.composables.SharedToast
import com.oliviermarteaux.shared.extensions.isValidEmail
import com.oliviermarteaux.a054_eventorias.R

/**
 * A screen for logging in or creating an account.
 *
 * @param modifier The modifier to apply to this screen.
 * @param navigateToPasswordScreen A function to call to navigate to the password screen.
 * @param navigateToHomeScreen A function to call to navigate to the home screen.
 * @param onBackClick A function to call when the back button is clicked.
 * @param loginViewModel The view model for this screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    logoDrawableRes: Int,
    modifier: Modifier = Modifier,
    navigateToPasswordScreen: (String) -> Unit,
    navigateToHomeScreen: () -> Unit,
    onBackClick: () -> Unit = {},
    loginViewModel: LoginViewModel = hiltViewModel()
){
    with (loginViewModel) {
        SharedScaffold(
            modifier = modifier,
            title = stringResource(R.string.sign_in),
            onBackClick = onBackClick,
        ) { contentPadding ->
            Box {
                LoginBody(
                    logoDrawableRes = logoDrawableRes,
                    newUser = newUser,
                    emailExist = emailExist,
                    isOnline = isOnline,
                    modifier = modifier
                        .padding(contentPadding)
                        .padding(horizontal = SharedPadding.xl)
                        .fillMaxSize(),
                    onEmailChange = ::onEmailChange,
                    onFirstNameChange = ::onFirstNameChange,
                    onLastNameChange = ::onLastNameChange,
                    onPasswordChange = ::onPasswordChange,
                    createAccount = ::createAccount,
                    checkEmail = ::checkEmail,
                    navigateToHomeScreen = navigateToHomeScreen,
                    navigateToPasswordScreen = navigateToPasswordScreen,
                    showNetworkErrorToast = ::showNetworkErrorToast,
                    onEmailExist = ::onEmailExist,
                )
                if(unknownError) SharedToast(text = stringResource(R.string.an_unknown_error_occurred))
                if(networkError) SharedToast(
                    text = stringResource(R.string.network_error_check_your_internet_connection),
                    bottomPadding = 120
                )
                if(accountCreationError) SharedToast(
                    text = stringResource(R.string.email_account_registration_unsuccessful),
                    bottomPadding = 160
                )
            }
        }
    }
}

/**
 * A composable for the body of the login screen.
 *
 * @param newUser The new user object.
 * @param emailExist A boolean indicating if the email exists.
 * @param isOnline A boolean indicating if the device is online.
 * @param modifier The modifier to apply to this composable.
 * @param onEmailChange A function to call when the email changes.
 * @param onFirstNameChange A function to call when the first name changes.
 * @param onLastNameChange A function to call when the last name changes.
 * @param onPasswordChange A function to call when the password changes.
 * @param createAccount A function to call to create an account.
 * @param checkEmail A function to call to check if an email exists.
 * @param navigateToHomeScreen A function to call to navigate to the home screen.
 * @param navigateToPasswordScreen A function to call to navigate to the password screen.
 * @param showNetworkErrorToast A function to call to show a network error toast.
 * @param onEmailExist A function to call when the email exists.
 */
@Composable
private fun LoginBody(
    logoDrawableRes: Int,
    newUser: NewUser,
    emailExist: Boolean?,
    isOnline: Boolean,
    modifier: Modifier = Modifier,
    onEmailChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    createAccount: (NewUser, () -> Unit) -> Unit,
    checkEmail: (String) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToPasswordScreen: (String) -> Unit,
    showNetworkErrorToast: () -> Unit,
    onEmailExist: (() -> Unit)-> Unit,
){
    var verticalArrangement: Arrangement.Vertical by remember { mutableStateOf(Arrangement.SpaceEvenly) }
    var scaffoldModifier: Modifier by remember { mutableStateOf(modifier) }

    IconScaffold(
        icon = IconSource.PainterIcon(painterResource(logoDrawableRes)),
        modifier = scaffoldModifier,
        verticalArrangement = verticalArrangement,
    ){
        SharedOutlinedEmail(
            value = newUser.email,
            onValueChange = { onEmailChange(it) },
            label = stringResource(R.string.email),
            imeAction = ImeAction.Done,
            modifier = Modifier.fillMaxWidth(),
            bottomPadding = SharedPadding.xxl,
            errorText = when {
                newUser.email.isEmpty() -> stringResource(R.string.enter_your_email_address_to_continue)
                !newUser.email.isValidEmail() -> stringResource(R.string.incorrect_email_address)
                else -> null
            }
        )
        when {
            emailExist == null -> {
                SharedButton(
                    onClick = { if (isOnline) checkEmail(newUser.email) else showNetworkErrorToast() },
                    text = stringResource(R.string.next),
                    enabled = newUser.email.run {isValidEmail() && isNotEmpty()}
                )
            }
            emailExist -> { onEmailExist{navigateToPasswordScreen(newUser.email) } }
            !emailExist -> {
                verticalArrangement = Arrangement.Top
                scaffoldModifier = modifier
                    .verticalScroll(rememberScrollState())
//                    .imePadding()
                val firstNameFocusRequester = remember { FocusRequester() }
                LaunchedEffect(Unit) { firstNameFocusRequester.requestFocus() }
                SharedOutlinedTextField(
                    value = newUser.firstname,
                    onValueChange = { onFirstNameChange(it) },
                    label = stringResource(R.string.first_name),
                    isError = newUser.firstname.isEmpty(),
                    errorText = stringResource(R.string.please_enter_a_first_name),
                    bottomPadding = SharedPadding.xxl,
                    modifier = Modifier
                        .focusRequester(firstNameFocusRequester)
                        .fillMaxWidth()
                )
                SharedOutlinedTextField(
                    value = newUser.lastname,
                    onValueChange = { onLastNameChange(it) },
                    label = stringResource(R.string.last_name),
                    isError = newUser.lastname.isEmpty(),
                    errorText = stringResource(R.string.please_enter_a_last_name),
                    bottomPadding = SharedPadding.xxl,
                    modifier = Modifier.fillMaxWidth()
                )
                SharedOutlinedPassword(
                    value = newUser.password,
                    onValueChange = { onPasswordChange(it) },
                    label = stringResource(R.string.new_password),
                    errorText = stringResource(R.string.password_is_not_strong_enough_use_at_least_6_characters_and_a_mix_of_letters_numbers_and_a_special_character),
                    passwordSetting = true,
                    modifier = Modifier.fillMaxWidth(),
                    bottomPadding = SharedPadding.xxl
                )
                SharedButton(
                    onClick = { createAccount(newUser){navigateToHomeScreen()} },
                    text = stringResource(R.string.save),
                    modifier = Modifier.padding(vertical = SharedPadding.xxl)
                )
                Spacer(modifier = Modifier.size(300.dp))
            }
        }
    }
}