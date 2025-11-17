package com.oliviermarteaux.localshared.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.oliviermarteaux.localshared.utils.hideKeyboard
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedSearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit,
    searchLabel: String = "",
    onSearch: (String) -> Unit = {},
    onIconClick: () -> Unit = {}
){
    val onActiveChange = { _: Boolean ->}
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = false,
                onExpandedChange = onActiveChange,
                placeholder = { Text(searchLabel) },
                trailingIcon = { SharedIconButton(
                    icon = IconSource.VectorIcon(Icons.Filled.Search),
                    contentDescription = searchLabel,
                    onClick = onIconClick
                ) },
                colors = inputFieldColors(),
            )
        },
        expanded = false,
        onExpandedChange = onActiveChange,
        modifier = modifier,
        colors = SearchBarDefaults.colors(),
        content = {},
    )
}