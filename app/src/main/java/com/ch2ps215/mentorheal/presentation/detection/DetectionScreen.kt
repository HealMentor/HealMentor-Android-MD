package com.ch2ps215.mentorheal.presentation.detection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.presentation.common.component.TopAppBar
import com.ch2ps215.mentorheal.presentation.detection.component.PickImageFromCamera
import com.ch2ps215.mentorheal.presentation.detection.component.PickImageFromGalery
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme


@Composable
fun DetectionScreen(
    navController: NavHostController,
    viewModel: DetectionViewModel = hiltViewModel(),
) {

    DetectionScreen(
        onNavigateBack = navController::popBackStack
    )
}

@Composable
fun DetectionScreen(
    onNavigateBack: () -> Unit,
) {

    val selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Pengaturan Penangkapan",
                onNavigationBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Judul dan deskripsi
            Text(
                text = "Pengaturan Penangkapan",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pastikan wajah terlihat ",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Pastikan Wajah Biasa Saja",
                style = MaterialTheme.typography.bodyMedium

            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PickImageFromGalery(modifier = Modifier.weight(1f))


                // Placeholder untuk ambil dari kamera (ganti dengan implementasi yang sesuai)
                PickImageFromCamera()
            }


            // Tampilkan gambar yang dipilih (jika ada)
            selectedImage?.let { image ->
                Image(
                    bitmap = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Implementasi logika deteksi
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Deteksi")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetectionScreenPreview() {
    MentorhealTheme {
        DetectionScreen(
            onNavigateBack = { }
        )
    }
}
