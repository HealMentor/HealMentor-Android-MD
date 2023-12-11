package com.ch2ps215.mentorheal.presentation.Camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.data.local.TfLiteUserClassifier
import com.ch2ps215.mentorheal.domain.model.Classification

@Composable
fun CameraMain(
    navHostController: NavHostController
) {
    val context: Context = LocalContext.current

    var classification by remember {
        mutableStateOf(emptyList<Classification>())
    }

    // Check and request camera permission if not granted
    if (!hasCameraPermission(context)) {
        // Request camera permission
        ActivityCompat.requestPermissions(
            LocalContext.current as Activity,
            arrayOf(Manifest.permission.CAMERA),
            0
        )
    }

    // Check if camera permission is granted before initializing the camera
    if (hasCameraPermission(context)) {
        val analyzer = remember {
            UserImageAnalyzer(
                classifier = TfLiteUserClassifier(
                    context = context
                ),
                onResult = {
                    classification = it
                }
            )
        }
        val controller = remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                setCameraSelector(CameraSelector.DEFAULT_FRONT_CAMERA)
                setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    analyzer
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CameraPreview(
                controller = controller, Modifier.fillMaxSize()
            )

            // Align the Column to the bottom center
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            ) {
                classification.forEach {
                    Text(
                        text = it.label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            }
        }

    } else {
        // Handle case where camera permission is not granted
        Text(
            text = "Camera permission not granted",
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error)
                .padding(8.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onError
        )
    }
}


@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}

private fun hasCameraPermission(context: Context) = ContextCompat.checkSelfPermission(
    context, Manifest.permission.CAMERA
) == PackageManager.PERMISSION_GRANTED