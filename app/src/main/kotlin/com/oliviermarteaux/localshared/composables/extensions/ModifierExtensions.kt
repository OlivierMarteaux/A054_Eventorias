package com.oliviermarteaux.localshared.composables.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

fun Modifier.cdSemantics(contentDescription: String)=
    this.clearAndSetSemantics { this.contentDescription = contentDescription }