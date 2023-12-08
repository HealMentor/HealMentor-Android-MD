package com.ch2ps215.mentorheal.presentation.form.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldGroup(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String?,
    additionalText: String = stringResource(id = R.string.depresi),
    onOptionSelected: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption ?: "") }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = additionalText,
            modifier = Modifier
                .weight(2f)
                .padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            modifier = Modifier
                .weight(1f)
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.please_choose),
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
                    .widthIn(100.dp)
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
}

@Preview(showBackground = true)
@Composable
fun TextFieldGroupPreview() {
    val option = listOf("Option 1", "Option 2")
    TextFieldGroup(
        options = option,
        selectedOption = "Option 1",
        onOptionSelected = { },
        additionalText = "Additional Text",
        modifier = Modifier.fillMaxWidth()
    )
}
