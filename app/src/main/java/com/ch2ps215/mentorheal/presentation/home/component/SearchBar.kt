package com.ch2ps215.mentorheal.presentation.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onBack: () -> Unit,
) {

    SearchBar(
        query = searchText,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = false,
        onActiveChange = { },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null
                )
            }
        },
        modifier = modifier
    ) {}
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    MentorhealTheme {
        SearchBar(
            query = "",
            onQueryChange = {},
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier,
        ) { }
    }
}
