package com.ch2ps215.mentorheal.presentation.form.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioGroup(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = {
                        onOptionSelected(option)
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
            }
        }
    }
}

