package com.oliviermarteaux.localshared.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Composable
fun SharedGoogleMap(
    modifier: Modifier = Modifier
) {
    // Center on Paris as example
    val paris = LatLng(48.8566, 2.3522)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(paris, 12f)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = true),
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
        // Example: Add a marker
//        Marker(
//            title = "Paris",
//            snippet = "City of Lights"
//        )
    }
}