package com.ch2ps215.mentorheal.data.repository

import com.ch2ps215.data.local.FormLocalDataSource
import com.ch2ps215.mentorheal.data.local.entity.FormEntity
import com.ch2ps215.mentorheal.domain.model.Form
import com.ch2ps215.mentorheal.domain.repository.IFormRepository
import kotlinx.coroutines.flow.Flow

class DefaultFormRepository(
    private val formLocalDataSource: FormLocalDataSource
) : IFormRepository {

    override suspend fun saveForm(
        umur: String,
        gender: String,
        bidang: String,
        semester: String,
        cgpa: String,
        pernikahan: String,
        depresi: String,
        kecemasan: String,
        panic: String,
        kebutuhankhusus: String,
        userId: String
    ): Form {
        val formEntity = FormEntity(
            umur = umur,
            gender = gender,
            bidang = bidang,
            semester = semester,
            cgpa = cgpa,
            pernikahan = pernikahan,
            depresi = depresi,
            kecemasan = kecemasan,
            panic = panic,
            kebutuhanKhusus = kebutuhankhusus,
            userId = userId
        )

        formLocalDataSource.saveForm(formEntity)
        return formEntity.toModel()
    }

    override fun getForms(): Flow<List<FormEntity>> {
        return formLocalDataSource.getForms()
    }

    override suspend fun deleteForm(formEntity: FormEntity) {
        formLocalDataSource.deleteForm(formEntity)
    }

    override suspend fun deleteAllForms() {
        formLocalDataSource.deleteAllForms()
    }

    private fun FormEntity.toModel(): Form {
        return Form(
            umur = umur,
            gender = gender,
            bidang = bidang,
            semester = semester,
            cgpa = cgpa,
            pernikahan = pernikahan,
            depresi = depresi,
            kecemasan = kecemasan,
            panic = panic,
            kebutuhanKhusus = kebutuhanKhusus,
            userId = userId
        )
    }
}
