// AddTrackerScreen.kt

package com.ch2ps215.mentorheal.presentation.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTrackerScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val density = LocalDensity.current.density
    val keyboardController = LocalSoftwareKeyboardController.current
    val persaanOptions = listOf("Good", "Great", "Bad", "Biasa Saja")
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            com.ch2ps215.mentorheal.presentation.common.component.TopAppBar(
                title = "Add Tracker",
                onClickNavigation = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            var title by remember { mutableStateOf("") }
            var selectedPerasaan by remember { mutableStateOf(persaanOptions[0]) }
            var deskripsi by remember { mutableStateOf("") }
            var rating by remember { mutableStateOf(1) }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Dropdown for perasaan
            DropdownMenu(
                expanded = false, // Initial state
                onDismissRequest = { /* Dismiss the menu */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                persaanOptions.forEach { perasaan ->
                    androidx.compose.material.DropdownMenuItem(onClick = {
                        selectedPerasaan = perasaan
                    }) {
                        Text(perasaan)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        // Toggle the dropdown menu
                        // You can handle this based on your UI requirements
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = selectedPerasaan)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }

            OutlinedTextField(
                value = deskripsi,
                onValueChange = { deskripsi = it },
                label = { Text("Deskripsi") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(120.dp)
            )

            // Rating bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                (1..5).forEach { i ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (i <= rating) colorResource(id = R.color.yellow) else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                rating = i
                            }
                    )
                }
            }

            // Button to submit data (you can customize the action)
            Button(
                onClick = {
                    // Perform action to submit data
                    // You may want to save the entered data to a database or perform other actions
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Submit")
            }
        }
    }
}

@Preview
@Composable
fun AddTrackerScreenPreview() {
    MentorhealTheme {
        AddTrackerScreen(onBack = { /* Handle back navigation in preview if needed */ })
    }
}
