package com.example.malar.translator.db

import android.arch.persistence.room.*
import com.example.malar.translator.models.Translation
import io.reactivex.Flowable

@Dao
interface TranslationsDao {

    @Query("SELECT * FROM translations")
    fun get() : Flowable<List<Translation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(translation: Translation)

    @Delete
    fun delete(vararg translation: Translation)
}