package com.oliviermarteaux.localshared.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedSearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    searchLabel: String = "",
){
    val onActiveChange = { _: Boolean ->}
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {},
                expanded = false,
                onExpandedChange = onActiveChange,
                placeholder = { Text(searchLabel) },
                trailingIcon = { Icon(Icons.Filled.Search, searchLabel) },
                colors = inputFieldColors(),
            )
        },
        expanded = false,
        onExpandedChange = onActiveChange,
        modifier = modifier.fillMaxWidth(),
        colors = SearchBarDefaults.colors(),
        content = {},
    )
}