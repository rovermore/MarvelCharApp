package com.example.marvelcharapp.presentation.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelcharapp.R
import com.example.marvelcharapp.databinding.CharacterItemBinding
import com.squareup.picasso.Picasso

class MainAdapter(
    private val characterList: MutableList<CharacterUIModel>,
    val itemClicked: OnItemClicked
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    interface OnItemClicked {
        fun itemClicked(character: CharacterUIModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(characterList.isNotEmpty()) {
            holder.bindView(characterList[position])
        }
    }

    override fun getItemCount(): Int {
        if(characterList.isEmpty()){
            return 0
        }
        return characterList.size
    }

    fun updateList(list: List<CharacterUIModel>) {
        list.forEach {
            characterList.add(it)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding  = CharacterItemBinding.bind(view)

        fun bindView(character: CharacterUIModel) {
            with(binding) {
                root.setOnClickListener {
                    itemClicked.itemClicked(character)
                }
                character.image?.let {
                    Picasso.with(binding.root.context)
                        .load(it.getImageUrl())
                        .error(R.drawable.ic_baseline_error_24)
                        //.placeholder(R.drawable.ic_marvel)
                        .into(characterImageView)
                }
                nameTextView.text = character.name
            }
        }
    }

}