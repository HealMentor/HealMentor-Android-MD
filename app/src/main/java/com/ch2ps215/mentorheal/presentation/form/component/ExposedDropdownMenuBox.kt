package com.ch2ps215.mentorheal.presentation.form.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.ch2ps215.mentorheal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String?,
    additionalText: String = stringResource(id = R.string.perkawinan),
    onOptionSelected: (String) -> Unit,
    label: String,
    isError: Boolean = false,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption ?: "") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedText,
            onValueChange = { selectedText = it },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            label = {
                Text(
                    text = additionalText,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
            },
            readOnly = true
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
        ) {
            options.forEach { label ->
                DropdownMenuItem(
                    modifier = Modifier,
                    text = { Text(text = label) },
                    onClick = {
                        selectedText = label
                        isExpanded = false
                        onOptionSelected(label)
                    })
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DropdownGroupPreview() {
    ExposedDropdownMenuBox(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedOption = "Option 1",
        onOptionSelected = { },
        modifier = Modifier.fillMaxWidth(),
        label = "Label",
    )
}