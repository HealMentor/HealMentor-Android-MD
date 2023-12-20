package com.ch2ps215.mentorheal.presentation.camera.component

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.ch2ps215.mentorheal.data.remote.TfLiteUserClassifierDataSource
import com.ch2ps215.mentorheal.domain.model.Classification
import com.ch2ps215.mentorheal.presentation.camera.UserImageAnalyzer
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CameraState(
    selector: CameraSelector,
    val preview: Preview,
    val imageCapture: ImageCapture,
    val imageAnalysis: ImageAnalysis,
    val analyzer: UserImageAnalyzer,
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context
) {
    var selector by mutableStateOf(selector)
    private val Context.executor: Executor
        get() = ContextCompat.getMainExecutor(this)

    init {
        setupImageAnalysis()
    }

    private fun setupImageAnalysis() {
        imageAnalysis.setAnalyzer(context.executor, analyzer)
    }

    suspend fun unbind() {
        val cameraProvider = getCameraProvider()
        cameraProvider.unbindAll()
    }

    suspend fun bind() {
        val cameraProvider = getCameraProvider()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            selector,
            preview,
            imageCapture,
            imageAnalysis
        )
    }

    suspend fun takePicture(): File {
        return imageCapture.takePicture(context.executor)
    }

    private suspend fun ImageCapture.takePicture(executor: Executor): File {
        val photoFile = runCatching {
            File.createTempFile("image", ".jpg").apply {
                Timber.i("")
            }
        }.getOrElse { e ->
            Timber.e(e, "Failed to create temporary file")
            File("/dev/null")
        }

        return suspendCancellableCoroutine { cont ->
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Timber.i("Image capture success")
                    cont.resume(photoFile)
                }

                override fun onError(ex: ImageCaptureException) {
                    Timber.e(ex, "Image capture failed")
                    cont.resumeWithException(ex)
                }
            })
        }
    }

    private suspend fun getCameraProvider(): ProcessCameraProvider =
        suspendCoroutine { continuation ->
            ProcessCameraProvider.getInstance(context).also { future ->
                future.addListener({
                    continuation.resume(future.get())
                }, context.executor)
            }
        }
}

@Composable
fun rememberCameraState(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    onResult: (List<Classification>) -> Unit,
) = remember {

    val preview = Preview
        .Builder()
        .build()

    val imageCapture = ImageCapture
        .Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()

    val resolutionSelector = ResolutionSelector.Builder()
        .setResolutionStrategy(ResolutionStrategy.HIGHEST_AVAILABLE_STRATEGY)

    val imageAnalysis = ImageAnalysis
        .Builder()
        .setResolutionSelector(resolutionSelector.build())
        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
        .build()

    val analyzer = UserImageAnalyzer(
        classifier = TfLiteUserClassifierDataSource(
            context = context
        ),
        onResult = onResult
    )

    CameraState(
        selector = CameraSelector.DEFAULT_BACK_CAMERA,
        lifecycleOwner = lifecycleOwner,
        context = context,
        preview = preview,
        imageCapture = imageCapture,
        imageAnalysis = imageAnalysis,
        analyzer = analyzer
    )
}
