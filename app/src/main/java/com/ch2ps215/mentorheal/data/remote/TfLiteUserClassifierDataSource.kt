package com.ch2ps215.mentorheal.data.remote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.Surface
import androidx.core.graphics.createBitmap
import com.ch2ps215.mentorheal.domain.model.Classification
import com.ch2ps215.mentorheal.domain.repository.UserClassification
import com.ch2ps215.mentorheal.presentation.common.BitmapUtils
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import timber.log.Timber
import java.io.File

class TfLiteUserClassifierDataSource(
    private val context: Context,
    private val threshold: Float = 0.5f,
    private val maxResult: Int = 3
) : UserClassification {

    private var classifier: ImageClassifier? = null

    private fun setupClassifier() {
        val baseOptions = BaseOptions.builder()
            .setNumThreads(2)
            .build()
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setBaseOptions(baseOptions)
            .setMaxResults(maxResult)
            .setScoreThreshold(threshold)
            .build()

        try {
            classifier = ImageClassifier.createFromFileAndOptions(
                context,
                "quant.tflite",
                options
            )
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    override fun classify(file: File): List<Classification> {
        if (classifier == null) {
            setupClassifier()
        }

        val bmp = BitmapFactory.decodeFile(file.path)

        val imageprocessor = ImageProcessor.Builder().build()
        val tensorImage = imageprocessor.process(TensorImage.fromBitmap(bmp))

        val result = classifier?.classify(tensorImage)

        Timber.d("result $result")

        return result?.flatMap { classification ->
            classification.categories.map { category ->
                Classification(
                    label = category.label,
                    scores = category.score.toInt()
                )
            }
        }?.distinctBy { it.label } ?: emptyList()
    }

    override fun classify(bitmap: Bitmap, rotation: Int): List<Classification> {
        if (classifier == null) {
            setupClassifier()
        }

        val imageprocessor = ImageProcessor.Builder().build()
        val tensorImage = imageprocessor.process(TensorImage.fromBitmap(bitmap))


        val imageProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(rotation))
            .build()

        val result = classifier?.classify(tensorImage, imageProcessingOptions)

        return result?.flatMap { classification ->
            classification.categories.map { category ->
                Classification(
                    label = category.label,
                    scores = category.score.toInt()
                )
            }
        }?.distinctBy { it.label } ?: emptyList()
    }

    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
        return when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }
}