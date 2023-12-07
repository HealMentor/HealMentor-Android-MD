package com.ch2ps215.mentorheal.presentation.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.presentation.common.component.noRippleClickable
import com.ch2ps215.mentorheal.R

val AppearanceOptions = mapOf(
    R.string.dark_theme to true,
    R.string.light_theme to false,
    R.string.system_default to null
)

@Composable
fun AppearanceDialog(
    modifier: Modifier = Modifier,
    selected: Boolean?,
    onChange: (Boolean?) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {},
        title = {
            Text(text = stringResource(R.string.appearance))
        },
        text = {
            Column {
                for ((nameRes, value) in AppearanceOptions) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                onChange(value)
                            },
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == value,
                            onClick = {
                                onChange(value)
                            }
                        )
                        Text(text = stringResource(nameRes))
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.save))
            }
        }
    )
}
