package com.oliviermarteaux.localshared.composables.extensions

import android.widget.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics

fun Modifier.cdButtonSemantics(
    contentDescription: String,
) =
    this.clearAndSetSemantics {
        this.contentDescription = contentDescription
        this.role = Role.Button
    }

//fun Modifier.cdButtonSemantics(
//    contentDescription: String,
//    onClick: () -> Unit = {}
//) = this.clearAndSetSemantics {
//    this.contentDescription = contentDescription
//    role = Role.Button
//    onClick {
//        onClick()
//        true
//    }
//}