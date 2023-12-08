package com.ch2ps215.data.local

import com.ch2ps215.mentorheal.data.local.entity.FormEntity
import com.ch2ps215.mentorheal.data.local.room.FormDao
import kotlinx.coroutines.flow.Flow

class FormLocalDataSource(
    private val formDao: FormDao
) {
    suspend fun saveForm(form: FormEntity) {
        formDao.save(form)
    }

    fun getForms(): Flow<List<FormEntity>> {
        return formDao.getForms()
    }

    suspend fun deleteForm(formEntity: FormEntity) {
        formDao.delete(formEntity)
    }

    suspend fun deleteAllForms() {
        formDao.deleteAll()
    }

}