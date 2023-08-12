package com.example.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.character.OnCharacterClickListener
import com.example.rickandmorty.ui.character.CharacterFragment
import com.example.rickandmorty.ui.character_detailed.CharacterDetailFragment

class MainActivity : AppCompatActivity(), OnCharacterClickListener {

    companion object {
        private const val tag = "TAG"
        private const val Character_Fragment_Tag = "Character_Fragment_Tag"
        private const val Character_Detail_Fragment_Tag = "Character_Detail_Fragment_Tag"
    }

    private var id: Int = 0
    private lateinit var binding: ActivityMainBinding
    private lateinit var characterFragment: CharacterFragment
    private lateinit var characterDetailFragment: CharacterDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myActionBar)

        characterFragment = CharacterFragment()
        characterDetailFragment = CharacterDetailFragment()

        setUpFragment(characterFragment, Character_Fragment_Tag)

        if (savedInstanceState != null) {

            val currentFragmentTag = savedInstanceState.getString("tag")
            this.id = savedInstanceState.getInt("id")

            when (currentFragmentTag) {

                Character_Fragment_Tag -> setUpFragment(characterFragment, Character_Fragment_Tag)

                Character_Detail_Fragment_Tag -> {
                    val bundle = Bundle()
                    bundle.putInt("Id", this.id)
                    characterDetailFragment.arguments = bundle
                    setUpFragment(
                        characterDetailFragment, Character_Detail_Fragment_Tag
                    )
                }

            }
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val currentFragmentTag =
            supportFragmentManager.findFragmentById(R.id.fragment_container)?.tag
        outState.putString("tag", currentFragmentTag)
        outState.putInt("id", this.id)
    }

    //Go to CharacterDetail
    override fun onClick(id: Int) {

        this.id = id

        val bundle = Bundle()
        bundle.putInt("Id", id)
        characterDetailFragment.arguments = bundle

        setUpFragment(characterDetailFragment, Character_Detail_Fragment_Tag)
    }

    private fun setUpFragment(fragment: Fragment, fragment_tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment_tag).commitNow()
    }

}