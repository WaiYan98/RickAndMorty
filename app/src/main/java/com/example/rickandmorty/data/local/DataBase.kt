package com.example.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty.data.entities.Character

@Database(entities = [Character::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun CharacterDao(): CharacterDao


}