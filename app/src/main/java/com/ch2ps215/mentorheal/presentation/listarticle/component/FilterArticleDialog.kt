package com.ch2ps215.mentorheal.presentation.listarticle.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Popup
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.listarticle.ListArticleType

@Composable
fun FilterArticleDialog(
    onDismiss: () -> Unit,
    onFilter: (ListArticleType) -> Unit
) {
    Popup(
        alignment = Alignment.TopEnd,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                TextButton(
                    onClick = { onFilter(ListArticleType.Favorite) }
                ) {
                    Text(text = stringResource(R.string.Favorite))
                }
                TextButton(
                    onClick = { onFilter(ListArticleType.Latest) }
                ) {
                    Text(text = stringResource(R.string.Latest))
                }
                TextButton(
                    onClick = { onFilter(ListArticleType.Happy) }
                ) {
                    Text(text = stringResource(R.string.Happy))
                }
                TextButton(
                    onClick = { onFilter(ListArticleType.Depression) }
                ) {
                    Text(text = stringResource(R.string.Depression))
                }
            }
        },
        onDismissRequest = onDismiss,
    )
}

@Preview
@Composable
fun FilterArticleDialogPreview() {
    FilterArticleDialog(onDismiss = {}, onFilter = {})
}
