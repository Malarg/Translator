package com.example.malar.translator.di

import com.example.malar.translator.adapters.FavouritesAdapter
import com.example.malar.translator.fragments.FavouritesFragment
import com.example.malar.translator.fragments.TranslatorFragment
import com.example.malar.translator.viewModels.FavouritesViewModel
import com.example.malar.translator.viewModels.TranslateViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(translateViewModel: TranslateViewModel)
    fun inject(translatorFragment: TranslatorFragment)
    fun inject(favouritesViewMode: FavouritesViewModel)
    fun inject(favouritesFragment: FavouritesFragment)
    fun inject(favouritesAdapter: FavouritesAdapter)
}