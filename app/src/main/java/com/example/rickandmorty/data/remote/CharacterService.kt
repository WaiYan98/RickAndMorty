package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.entities.RickAnaMortyCharacter
import io.reactivex.Observable
import retrofit2.http.GET

interface CharacterService {

    @GET("character")
    fun getAllCharacters(): Observable<RickAnaMortyCharacter>
}