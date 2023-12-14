package com.ch2ps215.mentorheal.presentation.tracker.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.R

@Composable
fun SearchWithAddButton(
    onSearchClick: (String) -> Unit,
    onAddClick: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearchClick(it)
            },
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
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
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
