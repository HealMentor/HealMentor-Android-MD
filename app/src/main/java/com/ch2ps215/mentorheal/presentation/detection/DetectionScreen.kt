package com.ch2ps215.mentorheal.presentation.detection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.presentation.Camera.CameraMain
import com.ch2ps215.mentorheal.presentation.atasikecemasan.AtasiKecemasanViewModel
import com.ch2ps215.mentorheal.presentation.detection.component.ImagePicker
import com.ch2ps215.mentorheal.presentation.detection.component.imageCaptureFromCamera
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetectionScreen(
    navController: NavHostController,
    viewModel: DetectionViewModel = hiltViewModel(),
    ) {

    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { "Detection" },
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


            // Pilihan upload atau ambil gambar
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Upload dari galeri
                ImagePicker(modifier = Modifier.weight(1f))

                // Ambil dari kamera
                // Placeholder untuk ambil dari kamera (ganti dengan implementasi yang sesuai)
                Button(
                    onClick = {
                        navController.navigate(Route.DetectionCamera())
                    },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(text = "Deteksi With Camera", color = Color.Black)
                }
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

            // Tombol deteksi (contoh)
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}


//@Preview
//@Composable
//fun DetectionScreenPreview() {
//    MentorhealTheme {
//        DetectionScreenView(onBack = { /* Handle back navigation in preview if needed */ })
//    }
//}
