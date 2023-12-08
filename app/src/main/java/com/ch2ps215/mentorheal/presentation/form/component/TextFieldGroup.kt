package com.ch2ps215.mentorheal.presentation.form.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.ch2ps215.mentorheal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldGroup(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    additionalText: String = stringResource(id = R.string.depresi),
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption ?: "") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = additionalText,
                modifier = Modifier
                    .padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                modifier = Modifier
                    .weight(1f)
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .widthIn(100.dp)
            ) {
                options.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedText = label
                        expanded = false
                        onOptionSelected(label)
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldGroupPreview() {
    TextFieldGroup(
        options = listOf("Option 1", "Option 2"),
        selectedOption = "Option 1",
        onOptionSelected = { },
        additionalText = "Additional Text",
        modifier = Modifier.fillMaxWidth()
    )
}
