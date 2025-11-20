package com.oliviermarteaux.a054_eventorias.ui.screen.account

import android.R.attr.label
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.composables.SharedBottomAppBar
import com.oliviermarteaux.localshared.composables.SharedOutlinedTextField
import com.oliviermarteaux.localshared.composables.SharedScaffold
import com.oliviermarteaux.localshared.firebase.authentication.domain.model.User
import com.oliviermarteaux.shared.composables.IconSource

@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel = hiltViewModel(),
    navController: NavController
) {
    with(accountViewModel) {
        SharedScaffold(
            title = "User profile",
//            trailingIcon = IconSource.PainterIcon(painterResource(id = R.drawable.martyna_siddeswara)),
            avatarUrl = user.photoUrl,
            bottomBar = { SharedBottomAppBar(navController = navController) }
        ) { paddingValues ->
            AccountScreenBody(
                user = user,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun AccountScreenBody(
    user: User,
    modifier: Modifier = Modifier
){
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
    ) {
        SharedOutlinedTextField(
            value = user.getComputedFullName(),
            label = stringResource(R.string.name),
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        SharedOutlinedTextField(
            value = user.email,
            label = stringResource(R.string.email),
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.notifications),
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AccountScreenPreview() {
//    AccountScreen(navController = rememberNavController())
//}