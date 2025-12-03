package com.oliviermarteaux.localshared.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    searchBarIconModifier: Modifier = Modifier,
    searchLabel: String = "",
    searchBarIcon: IconSource = IconSource.VectorIcon(Icons.Filled.Search),
    onSearchBarIconClick: () -> Unit = {},
    searchBarIconContentDescription: String = ""
){
    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        Surface(
            modifier = Modifier.height(48.dp).fillMaxWidth().clip(CircleShape),
            color = SearchBarDefaults.colors().containerColor
        ){}
        Row(
            modifier = modifier
                .clip(shape = CircleShape)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Text field
            SharedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = textFieldModifier
                    .weight(1f),
                placeholder = searchLabel,
                singleLine = true,
                imeAction = ImeAction.Search,
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch() }
                ),
            )

            // Trailing icon (fully accessible)
            SharedIconButton(
                icon = searchBarIcon,
                contentDescription = searchBarIconContentDescription,
                modifier = searchBarIconModifier,
                onClick = onSearchBarIconClick
            )
        }
    }
}