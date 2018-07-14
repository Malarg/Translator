package com.example.malar.translator.db

import android.content.Context
import android.os.AsyncTask
import com.example.malar.translator.R
import com.example.malar.translator.models.Translation
import com.example.malar.translator.services.GetLanguagesResponse
import com.example.malar.translator.services.GetTranslationResponse
import com.example.malar.translator.services.TranslateService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class Repository @Inject constructor(private val context: Context) {
    private val db: AppDatabase = AppDatabase.getDatabase(context)
    private val translationsDao = db.translationsDao()

    private val translationServiceBuilder = Retrofit
            .Builder()
            .baseUrl("https://translate.yandex.net")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private val translateService = translationServiceBuilder.create(TranslateService::class.java)

    fun getTranslations() = translationsDao.get()

    fun insertTranslation(translation: Translation): AsyncTask<Translation, Void, Void> = InsertTranslationAsyncTask(translationsDao).execute(translation)
    private class InsertTranslationAsyncTask(val dao: TranslationsDao) : AsyncTask<Translation, Void, Void>() {
        override fun doInBackground(vararg params: Translation?): Void? {
            val translation = params[0]
                    ?: throw NullPointerException("Expression 'params[0]' must not be null")
            dao.insert(translation)
            return null
        }
    }

    fun deleteTranslation(translation: Translation): AsyncTask<Translation, Void, Void> = DeleteTranslationAsyncTask(translationsDao).execute(translation)
    private class DeleteTranslationAsyncTask(val dao: TranslationsDao) : AsyncTask<Translation, Void, Void>() {
        override fun doInBackground(vararg params: Translation?): Void? {
            val translation = params[0]
                    ?: throw NullPointerException("Expression 'params[0]' must not be null")
            dao.delete(translation)
            return null
        }
    }

    fun translate(sourceText: String, sourceLangCode: String, destinationLangCode: String) : Flowable<GetTranslationResponse>  {
        return translateService.getTranslation(context.getString(R.string.translate_api_key), sourceText, "$sourceLangCode-$destinationLangCode")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    fun getLanguages(): Flowable<GetLanguagesResponse>? {
        return translateService.getLanguages(context.getString(R.string.translate_api_key), "en")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


}