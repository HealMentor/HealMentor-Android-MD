package com.ch2ps215.mentorheal.presentation.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ch2ps215.mentorheal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String?,
    onClickNavigation: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title ?: "")
        },
        navigationIcon = {
            IconButton(onClick = onClickNavigation) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = stringResource(R.string.cd_back)
                )
            }
        }
    )
}
