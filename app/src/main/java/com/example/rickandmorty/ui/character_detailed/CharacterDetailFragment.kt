package com.example.rickandmorty.ui.character_detailed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.CharacterDetailFragmentBinding
import com.example.rickandmorty.ui.character.CharacterFragment

class CharacterDetailFragment : Fragment() {

    private lateinit var binding: CharacterDetailFragmentBinding

    private val viewModel: CharacterDetailViewModel by viewModels()
    private var id: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        customizeToolBar()
        binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        if (bundle != null) {

            id = bundle.getInt("Id")

            if (id != 0) {
                id.let {
                    setId(it)
                    Toast.makeText(context, "$id", Toast.LENGTH_LONG).show()
                }
            }
        }

        setupViewModel()

    }

    private fun setId(id: Int) {
        viewModel.setId(id)
    }

    private fun setupViewModel() {

        Log.d("mytag", "setupViewModel: viewModel point")

        viewModel.getId()
            .observe(viewLifecycleOwner) {
                Log.d("idTest", "setupViewModel: $it ")

                viewModel.getCharacterById(it)
            }

        viewModel.getCharacter()
            .observe(viewLifecycleOwner) {

                //update ui
                binding.txtCharacterName.text = it.name
                binding.txtSpecies.text = it.species
                binding.txtStatus.text = it.species
                binding.txtGender.text = it.gender

                Glide.with(this)
                    .load(it.image)
                    .circleCrop()
                    .into(binding.imgCharacter)


            }

    }

    private fun customizeToolBar() {
        val toolBar = activity?.findViewById<Toolbar>(R.id.my_action_bar)

        if (toolBar != null) {

            toolBar.apply {
                setNavigationIcon(R.drawable.arrow_back_24)
                setTitle(R.string.toolbar_title_character_detail)
                setTitleTextColor(context.getColor(R.color.black))

                setNavigationOnClickListener {

                    fragmentManager?.beginTransaction()
                        ?.replace(
                            R.id.fragment_container,
                            CharacterFragment(),
                            "Character_Fragment_Tag"
                        )
                        ?.commitNow()
                }
            }
        }
    }
}