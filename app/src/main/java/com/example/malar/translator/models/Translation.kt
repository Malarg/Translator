package com.example.malar.translator.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "translations")
class Translation(@ColumnInfo(name = "sourceLanguage")
                  var sourceLanguage: String,

                  @ColumnInfo(name = "destinationLanguage")
                  var destinationLanguage: String,

                  @ColumnInfo(name = "sourceText")
                  var sourceText: String,

                  @ColumnInfo(name = "translatedText")
                  var translatedText: String,

                  @PrimaryKey(autoGenerate = true)
                  @ColumnInfo(name = "id")
                  var id: Long = 0)