package com.example.malar.translator.viewModels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.malar.translator.db.Repository
import com.example.malar.translator.di.App
import com.example.malar.translator.models.Translation
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(repository: Repository): ViewModel() {

    val translations = MutableLiveData<List<Translation>>()

    init {
        App.getComponent().inject(this)
        repository.getTranslations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            translations.value = it
        }
    }

}