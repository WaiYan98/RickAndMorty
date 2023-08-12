package com.example.rickandmorty.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.local.DatabaseClient
import com.example.rickandmorty.data.remote.RetrofitClient
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Repository(private val context: Context) {

    private val characterService = RetrofitClient.characterService
    private val database = DatabaseClient.getInstance(context)
    private val dao = database.CharacterDao()
    private var characterList = listOf<Character>()


    //remote
    fun getAllCharactersRemote(): Observable<List<Character>> {
        return characterService.getAllCharacters()
            .map {
                it.characterList
            }

    }

    //remote response =>dataList insert into local database
    fun insertAll(data: List<Character>): Completable {
        return dao.insertAllCharacters(data)
    }

    //local
    fun getAllCharacterLocal(): Observable<List<Character>> {
        return dao.getAllCharacters()
    }

    //To get num of rows
    fun getCount(): Single<Int> = dao.getCountRow()


    fun getCharacterList(): List<Character> {
        return this.characterList
    }

    fun getCharacterById(id: Int): Single<Character> {
        return dao.getById(id)
    }
}
