package com.oliviermarteaux.localshared.composables.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliviermarteaux.localshared.ui.theme.SharedPadding

/**
 * 16.dp
 */
@Composable
fun SpacerMedium() = Spacer(modifier = Modifier.size(SharedPadding.medium))