package com.example.rickandmorty.data.entities

import com.google.gson.annotations.SerializedName

data class RickAnaMortyCharacter(
    var info: Info,
    @SerializedName("results")
    var characterList: List<Character>
)