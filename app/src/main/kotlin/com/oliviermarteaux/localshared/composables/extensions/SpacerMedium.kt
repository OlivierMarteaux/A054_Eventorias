package com.oliviermarteaux.localshared.composables.extensions

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliviermarteaux.localshared.ui.theme.SharedPadding

@Composable
fun SpacerMedium() = Spacer(modifier = Modifier.size(SharedPadding.medium))