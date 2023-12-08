package com.ch2ps215.mentorheal.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch2ps215.mentorheal.data.local.entity.FormEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FormDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(formEntity: FormEntity)

    @Query("SELECT * FROM form_keluhan ORDER BY id DESC")
    fun getForms(): Flow<List<FormEntity>>

    @Delete
    suspend fun delete(formEntity: FormEntity)

    @Query("DELETE FROM form_keluhan")
    suspend fun deleteAll()
}
