package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.data.local.entity.FormEntity
import com.ch2ps215.mentorheal.domain.model.Form
import kotlinx.coroutines.flow.Flow

interface IFormRepository {

    suspend fun saveForm(
        umur: String, gender: String, bidang: String, semester: String,
        cgpa: String, pernikahan: String, depresi: String, kecemasan: String,
        panic: String, kebutuhankhusus: String, userId: String
    ): Form

    fun getForms(): Flow<List<FormEntity>>

    suspend fun deleteForm(formEntity: FormEntity)

    suspend fun deleteAllForms()
}
