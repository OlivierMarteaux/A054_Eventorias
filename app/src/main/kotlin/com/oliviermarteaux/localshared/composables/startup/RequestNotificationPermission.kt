package com.oliviermarteaux.localshared.composables.startup

//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.isGranted
//import com.google.accompanist.permissions.rememberPermissionState
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun RequestNotificationPermission() {
//    val notificationPermissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
//
//    LaunchedEffect(Unit) {
//        if (!notificationPermissionState.status.isGranted) {
//            notificationPermissionState.launchPermissionRequest()
//        }
//    }
//}