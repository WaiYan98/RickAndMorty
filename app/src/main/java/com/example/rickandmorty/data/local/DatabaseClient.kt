package com.example.rickandmorty.data.local

import android.content.Context
import androidx.room.Room

class DatabaseClient {

    companion object {

        private const val DATABASE_NAME = "Character.db"
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME)
                    .build()
            }

            return INSTANCE as DataBase
        }
    }
}