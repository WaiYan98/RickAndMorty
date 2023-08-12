package com.example.rickandmorty.ui.character

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.CharacterFragmentBinding

class CharacterFragment : Fragment() {

    private lateinit var binding: CharacterFragmentBinding
    private val characterRecycleViewAdapter = CharacterRecycleViewAdapter()
    private val characterViewModel: CharacterViewModel by viewModels()
    private var onCharacterClickListener: OnCharacterClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCharacterClickListener) {
            onCharacterClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customizeToolBar()
        setUpLoading()
        setUpErrorMessage()
        setUpViewModel()
        setupRecycleView()
    }

    override fun onDetach() {
        super.onDetach()
        onCharacterClickListener = null
    }

    private fun setUpViewModel() {

        characterViewModel.hasDataInLocal()
            .observe(viewLifecycleOwner) {

                when (it) {

                    true -> characterViewModel.getAllDataFromLocal()

                    else -> characterViewModel.getAllDataFromRemote()
                }
            }

        characterViewModel.getAllCharacter()
            .observe(viewLifecycleOwner) {
                //todo change flow
                characterRecycleViewAdapter.setDataSet(it)
            }
    }

    private fun setupRecycleView() {

        binding.recyCharacter.apply {
            adapter = characterRecycleViewAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        characterRecycleViewAdapter.setCallBack(onCharacterClickListener)
    }

    private fun setUpErrorMessage() {

        characterViewModel.getErrorMessage()
            .observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
    }

    private fun setUpLoading() {

        characterViewModel.getLoading()
            .observe(viewLifecycleOwner) {
                when (it) {
                    true -> binding.loadingAnimation.visibility = View.VISIBLE

                    else -> binding.loadingAnimation.visibility = View.GONE
                }
            }
    }

    private fun customizeToolBar() {

        val toolBar: Toolbar? = activity?.findViewById(R.id.my_action_bar)

        if (toolBar != null) {

            toolBar.apply {
                setNavigationIcon(R.drawable.white_colour)
                title = "Characters"
                setTitle(R.string.toolbar_title_character)
                setTitleTextColor(context.getColor(R.color.black))
            }
        }
    }

}

