package com.ch2ps215.mentorheal.presentation.common.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@Composable
fun TextError(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int?
) {
    Text(
        text = textRes?.let { stringResource(it) } ?: " ",
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier.padding(top = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TextErrorPreview() {
    MentorhealTheme {
        TextError(textRes = R.string.error_name_is_required)
    }
}
