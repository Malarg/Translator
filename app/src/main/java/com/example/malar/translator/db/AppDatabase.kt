package com.example.malar.translator.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.malar.translator.models.Translation

@Database(entities = [Translation::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationsDao(): TranslationsDao

    companion object {
        private var databaseInstance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (databaseInstance == null) synchronized(AppDatabase::class.java) {
                if (databaseInstance == null) {
                    databaseInstance = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "app_database")
                            .fallbackToDestructiveMigration()
                            .build()

                }
            }
            return databaseInstance
                    ?: throw NullPointerException("Expression 'databaseInstance' must not be null")
        }
    }
}