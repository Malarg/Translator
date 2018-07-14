package com.example.malar.translator.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.malar.translator.R
import com.example.malar.translator.di.App
import com.example.malar.translator.models.Translation
import com.example.malar.translator.viewModels.TranslateViewModel
import com.example.malar.translator.viewModels.ViewModelFactory
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TranslatorFragment @Inject constructor(): Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_translator, container, false)
        App.getComponent().inject(this)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[TranslateViewModel::class.java]
        loadLanguages(view, viewModel)
        return view
    }

    private fun loadLanguages(view: View, viewModel: TranslateViewModel) {
        val sourceLangSpinner = view.findViewById<Spinner>(R.id.sourceLanguageSpinner)
        val destinationLangSpinner = view.findViewById<Spinner>(R.id.destinationLanguageSpinner)
        viewModel.availableLanguages.observe(this, Observer { value ->
            sourceLangSpinner.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, value?.map { it.title })
            destinationLangSpinner.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, value?.map { it.title })
            onLanguagesLoaded(view, viewModel)
        })
    }

    private fun onLanguagesLoaded(view: View, viewModel: TranslateViewModel) {
        observeTranslationUpdating(view, viewModel)
        handleViewEvents(view, viewModel)
        handleViewModelChange(view, viewModel)
    }

    private fun observeTranslationUpdating(view: View, viewModel: TranslateViewModel) {
        val translatedTextTV = view.findViewById<TextView>(R.id.translatedTextTV)
        viewModel.translatedText.observe(this, Observer {
            translatedTextTV.text = it
        })
    }

    private fun handleViewEvents(view: View, viewModel: TranslateViewModel) {
        val sourceLangSpinner = view.findViewById<Spinner>(R.id.sourceLanguageSpinner)
        val destinationLangSpinner = view.findViewById<Spinner>(R.id.destinationLanguageSpinner)
        val sourceTextEditText = view.findViewById<EditText>(R.id.sourceTextTV)

        RxTextView.textChanges(sourceTextEditText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewModel.sourceText.value = it.toString() }

        RxAdapterView.itemSelections(sourceLangSpinner)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewModel.sourceLanguagePosition.value = it }

        RxAdapterView.itemSelections(destinationLangSpinner)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewModel.destinationLanguagePosition.value = it }

        val swapLanguagesButton = view.findViewById<ImageButton>(R.id.swapLanguagesButton)
        RxView.clicks(swapLanguagesButton)
                .subscribe {
                    val positionKeeper = sourceLangSpinner.selectedItemPosition
                    sourceLangSpinner.setSelection(destinationLangSpinner.selectedItemPosition)
                    destinationLangSpinner.setSelection(positionKeeper)
                }

        val addToFavouriteButton = view.findViewById<ImageButton>(R.id.addToFavouriteButton)
        RxView.clicks(addToFavouriteButton)
                .subscribe {
                    viewModel.insertTranslation(createTranslation(view, viewModel))
                    addToFavouriteButton.setImageResource(android.R.drawable.btn_star_big_on)
                }
    }

    private fun createTranslation(view: View, viewModel: TranslateViewModel): Translation {
        val sourceText = view.findViewById<EditText>(R.id.sourceTextTV).text.toString()
        val translatedText = view.findViewById<TextView>(R.id.translatedTextTV).text.toString()
        val selectedSourceLanguagePosition = view.findViewById<Spinner>(R.id.sourceLanguageSpinner).selectedItemPosition
        val sourceLanguage = viewModel.availableLanguages.value?.get(selectedSourceLanguagePosition)?.title
        val selectedDestinationLanguagePosition = view.findViewById<Spinner>(R.id.destinationLanguageSpinner).selectedItemPosition
        val destinationLanguage = viewModel.availableLanguages.value?.get(selectedDestinationLanguagePosition)?.title
        return Translation(sourceLanguage ?: "", destinationLanguage ?: "", sourceText, translatedText)
    }

    private fun handleViewModelChange(view: View, viewModel: TranslateViewModel) {
        val addToFavouriteButton = view.findViewById<ImageButton>(R.id.addToFavouriteButton)
        viewModel.sourceText.observe(this, Observer {
            viewModel.updateTranslation()
            addToFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
        })
        viewModel.sourceLanguagePosition.observe(this, Observer {
            viewModel.updateTranslation()
            addToFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
        })
        viewModel.destinationLanguagePosition.observe(this, Observer {
            viewModel.updateTranslation()
            addToFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                TranslatorFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
