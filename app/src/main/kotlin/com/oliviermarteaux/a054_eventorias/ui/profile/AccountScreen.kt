package com.oliviermarteaux.a054_eventorias.ui.profile

//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Switch
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.modifier.modifierLocalConsumer
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.oliviermarteaux.a054_eventorias.R
//import com.oliviermarteaux.localshared.composables.SharedBottomAppBar
//import com.oliviermarteaux.localshared.composables.SharedScaffold
//import com.oliviermarteaux.localshared.ui.navigation.Screen
//import com.oliviermarteaux.shared.composables.IconSource
//
//@Composable
//fun AccountScreen(
//    navController: NavController
//) {
//    SharedScaffold(
//        title = "User profile",
//        trailingIcon = IconSource.PainterIcon(painterResource(id = R.drawable.martyna_siddeswara)),
//        bottomBar = { SharedBottomAppBar(navController = navController) }
//    ) { paddingValues ->
//        AccountScreenBody(
//            paddingValues = paddingValues,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp)
//        )
//    }
//}
//
//@Composable
//fun AccountScreenBody(
//    paddingValues: PaddingValues,
//    modifier: Modifier = Modifier
//){
//    var notificationsEnabled by remember { mutableStateOf(true) }
//    Column(
//        modifier = modifier
//    ) {
//        OutlinedTextField(
//            value = "Martyna Siddeswara",
//            onValueChange = {},
//            label = { Text("Name") },
//            modifier = Modifier.fillMaxWidth(),
//            readOnly = true
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = "christopherevans@gmail.com",
//            onValueChange = {},
//            label = { Text("E-mail") },
//            modifier = Modifier.fillMaxWidth(),
//            readOnly = true
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = "Notifications",
//                style = MaterialTheme.typography.bodyLarge
//            )
//
//            Switch(
//                checked = notificationsEnabled,
//                onCheckedChange = { notificationsEnabled = it }
//            )
//        }
//    }
//}
//
////@Preview(showBackground = true)
////@Composable
////fun AccountScreenPreview() {
////    AccountScreen(navController = rememberNavController())
////}
