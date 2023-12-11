package com.ch2ps215.mentorheal.presentation.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@Composable
fun OutlinedTextFieldPassword(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.password),
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    var visible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Password),
        keyboardActions = keyboardActions,
        visualTransformation = if (visible) visualTransformation else PasswordVisualTransformation(),
        maxLines = maxLines,
        singleLine = singleLine,
        label = {
            Text(text = label)
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    visible = !visible
                }
            ) {
                if (visible) {
                    Icon(
                        imageVector = Icons.Outlined.Visibility,
                        contentDescription = stringResource(R.string.cd_pass_visibility)
                    )
                    return@IconButton
                }
                Icon(
                    imageVector = Icons.Outlined.VisibilityOff,
                    contentDescription = stringResource(R.string.cd_pass_visibility)
                )
            }
        },
        isError = isError
    )
}

@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldPasswordPreview() {
    MentorhealTheme {
        OutlinedTextFieldPassword(
            value = "naruto",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
