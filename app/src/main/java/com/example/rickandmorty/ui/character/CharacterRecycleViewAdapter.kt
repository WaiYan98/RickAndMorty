package com.example.rickandmorty.ui.character

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.databinding.ItemCharacterBinding

class CharacterRecycleViewAdapter : RecyclerView.Adapter<CharacterRecycleViewAdapter.ViewHolder>() {

    private var data: List<Character> = listOf()
    private var callBack: OnCharacterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentCharacter = data[position]

        holder.bind(currentCharacter)

        holder.itemView.setOnClickListener {
            callBack?.onClick(currentCharacter.id)
        }
    }

    class ViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentCharacter: Character) {

            binding.txtCharacterName.text = currentCharacter.name
            binding.txtSpeciesStatus.text = "${currentCharacter.species}-${currentCharacter.status}"

            Glide.with(binding.root)
                .load(currentCharacter.image)
                .circleCrop()
                .into(binding.imgCharacter)
        }
    }

    fun setCallBack(callBack: OnCharacterClickListener?) {
        this.callBack = callBack
    }

    fun setDataSet(data: List<Character>) {
        Log.d("myTest", "setDataSet: $data")
        this.data = emptyList()
        this.data = data
        notifyDataSetChanged()
    }
}

interface OnCharacterClickListener {

    fun onClick(id: Int)

}