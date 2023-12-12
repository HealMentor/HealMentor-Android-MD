package com.ch2ps215.mentorheal.presentation.camera

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.ch2ps215.mentorheal.domain.model.Classification
import com.ch2ps215.mentorheal.domain.repository.UserClassification

class UserImageAnalyzer(
    private val classifier: UserClassification,
    private val onResult: (List<Classification>) -> Unit
) : ImageAnalysis.Analyzer {

    private var framecounter = 0
    override fun analyze(image: ImageProxy) {
        if (framecounter % 60 == 0) {
            val rotationDegress = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(224, 224)

            val result = classifier.classify(bitmap, rotationDegress)
            onResult(result)
        }

        framecounter++

        image.close()
    }
}