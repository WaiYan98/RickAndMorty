package com.example.rickandmorty.ui.character_detailed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.Repository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.map

class CharacterDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = Repository(application.applicationContext)
    private val character = MutableLiveData<Character>()
    private val errorMessage = MutableLiveData<String>()
    private val isLoading = MutableLiveData<Boolean>()
    private var id = MutableLiveData<Int>()
    private val compositeDisposable = CompositeDisposable()

    init {

    }

    fun getCharacterById(id: Int) {
        isLoading.value = true

        val disposable = repo.getCharacterById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                character.value = it
                isLoading.value = false
            }) {
                errorMessage.value = it.message
            }

        compositeDisposable.add(disposable)
    }


    fun getCharacter() = character

    fun isLoading() = isLoading

    fun getErrorMessage() = errorMessage

    fun setId(id: Int) {
        this.id.value = id
    }

    fun getId() = this.id

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}