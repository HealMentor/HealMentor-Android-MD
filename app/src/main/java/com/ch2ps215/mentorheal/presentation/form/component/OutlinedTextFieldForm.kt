package com.ch2ps215.mentorheal.presentation.form.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@Composable
fun OutlineTextFieldForm(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.umur),
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Password),
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        singleLine = singleLine,
        label = {
            Text(text = label)
        },

        isError = isError
    )
}

@Preview(showBackground = true)
@Composable
fun OutlineTextFieldFormPreview() {
    MentorhealTheme {
        OutlineTextFieldForm(
            value = "20",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
