package com.ch2ps215.mentorheal.presentation.tracker.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWithAddButton(
    onSearchClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(stringResource(id = R.string.search_hint)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = onAddClick,
            modifier = Modifier
                .height(56.dp)
                .width(120.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Add")
        }
    }
}

@Preview
@Composable
fun SearchWithAddButtonPreview() {
    SearchWithAddButton(
        onSearchClick = {},
        onAddClick = {}
    )
}
