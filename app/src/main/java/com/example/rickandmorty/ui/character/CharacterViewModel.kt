package com.example.rickandmorty.ui.character

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application.applicationContext)
    private var charactersResponse = MutableLiveData<List<Character>>()
    private var isLoading = MutableLiveData<Boolean>()
    private var hasDataInLocal = MutableLiveData<Boolean>()
    private var errorMessage = MutableLiveData<String>()
    private var compositeDisposable = CompositeDisposable()

    init {
        hasLocalData()
    }

    fun getAllCharacter(): LiveData<List<Character>> {
        return charactersResponse
    }

    fun getAllDataFromLocal() {
        isLoading.value = true
        val disposable = repository.getAllCharacterLocal()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                charactersResponse.value = it
            }, {

                errorMessage.value = it.message
            }) {
                isLoading.value = false
            }

        compositeDisposable.add(disposable)
    }

    fun getAllDataFromRemote() {
        isLoading.value = true
        val disposable = repository.getAllCharactersRemote()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                charactersResponse.value = it
                insertDatabase(it)
            }, {
                errorMessage.value = it.localizedMessage
            }, {
                isLoading.value = false
            })

        compositeDisposable.add(disposable)
    }


    private fun insertDatabase(data: List<Character>) {
        isLoading.value = true
        val disposable = repository.insertAll(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("hack", "insertDatabase: save")
                isLoading.value = false
            }) {
                errorMessage.value = it.message
            }
        compositeDisposable.add(disposable)
    }


    //check database has data
    private fun hasLocalData() {
        isLoading.value = true
        val disposable = repository.getCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hasDataInLocal.value = when (it) {
                    0 -> false
                    else -> true
                }
                isLoading.value = false
            }) {
                Log.d("tag", "hasData:  ${it.message} ")
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun getLoading(): LiveData<Boolean> = isLoading

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun hasDataInLocal() = hasDataInLocal
}