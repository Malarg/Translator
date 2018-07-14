package com.example.malar.translator.services

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateService {
    @GET("api/v1.5/tr.json/translate")
    fun getTranslation(@Query("key") key: String,
                       @Query("text") text: String,
                       @Query("lang") lang: String): Flowable<GetTranslationResponse>

    @GET("api/v1.5/tr.json/getLangs")
    fun getLanguages(@Query("key") key: String,
                     @Query("ui") ui: String): Flowable<GetLanguagesResponse>
}

class GetTranslationResponse(var code: String = "",
                             var lang: String = "",
                             var text: List<String> = emptyList())

class GetLanguagesResponse(var langs: Map<String, String> = emptyMap())