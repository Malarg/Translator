package com.example.malar.translator.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.malar.translator.R
import com.example.malar.translator.db.Repository
import com.example.malar.translator.di.App
import com.example.malar.translator.models.Translation
import javax.inject.Inject

class FavouritesAdapter @Inject constructor() : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    @Inject
    lateinit var repository: Repository

    private var translations : List<Translation> = emptyList()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FavouritesAdapter.ViewHolder {
        App.getComponent().inject(this)
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.tranlation_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = translations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val translation = translations[position]
        holder.view.let {
            it.findViewById<TextView>(R.id.sourceTextTV).text = translation.sourceText
            it.findViewById<TextView>(R.id.translatedTextTV).text = translation.translatedText
            it.findViewById<TextView>(R.id.sourceLanguageTV).text = translation.sourceLanguage
            it.findViewById<TextView>(R.id.destinationLanguageTV).text = translation.destinationLanguage
            it.findViewById<ImageButton>(R.id.deleteFavouriteButton).setOnClickListener {
                repository.deleteTranslation(translations[position])
            }
        }
    }

    fun update(translations: List<Translation>) {
        this.translations = translations
        notifyDataSetChanged()
    }
}