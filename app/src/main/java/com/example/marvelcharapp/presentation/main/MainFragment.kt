package com.example.marvelcharapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.example.marvelcharapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MainFragmentView(
                    onCharacterClicked = { itemClicked(it) }
                )
            }
        }
    }

    private fun itemClicked(character: CharacterUIModel) {
        val bundle = bundleOf("characterID" to character.id)
        NavHostFragment.findNavController(this).navigate(R.id.action_MainFragment_to_DetailFragment, bundle)
    }

}