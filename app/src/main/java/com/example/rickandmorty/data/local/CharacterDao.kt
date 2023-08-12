package com.example.rickandmorty.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.entities.Character
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character")
    fun getAllCharacters(): Observable<List<Character>>

    //to do insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCharacters(characterList: List<Character>): Completable

    @Query("SELECT COUNT (*) FROM Character")
    fun getCountRow(): Single<Int>

    @Query("SELECT * FROM Character WHERE id = :id")
    fun getById(id: Int): Single<Character>
}