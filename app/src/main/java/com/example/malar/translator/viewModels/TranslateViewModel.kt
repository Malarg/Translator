package com.example.malar.translator.viewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.example.malar.translator.db.Repository
import com.example.malar.translator.di.App
import com.example.malar.translator.models.Language
import com.example.malar.translator.models.Translation
import javax.inject.Inject

class TranslateViewModel @Inject constructor(private var repository: Repository): ViewModel() {

    val availableLanguages = MutableLiveData<List<Language>>()

    val translatedText = MutableLiveData<String>()

    val sourceText = MutableLiveData<String>()

    val sourceLanguagePosition = MutableLiveData<Int>()

    val destinationLanguagePosition = MutableLiveData<Int>()

    init {
        App.getComponent().inject(this)
        repository.getLanguages()?.subscribe {
            availableLanguages.value = it.langs.map { Language(it.key, it.value) }
        }
    }

    fun updateTranslation() {
        val assertedSourceText = sourceText.value ?: return
        if (assertedSourceText.isEmpty()) return
        if (sourceLanguagePosition.value == destinationLanguagePosition.value && destinationLanguagePosition.value == -1) return
        val assertedSourceLanguageFromAvailable = availableLanguages.value?.get(sourceLanguagePosition.value ?: return)
        val assertedSourceLanguage = assertedSourceLanguageFromAvailable ?: return
        val assertedDestinationLanguageFromAvailable = availableLanguages.value?.get(destinationLanguagePosition.value ?: return)
        val assertedDestinationLanguage = assertedDestinationLanguageFromAvailable ?: return

        repository.translate(assertedSourceText, assertedSourceLanguage.shortTitle, assertedDestinationLanguage.shortTitle)
                .subscribe {
                    translatedText.value = it.text[0]
                }
    }

    fun insertTranslation(translation: Translation): AsyncTask<Translation, Void, Void> = repository.insertTranslation(translation)
}