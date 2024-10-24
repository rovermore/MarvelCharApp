package com.example.marvelcharapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.marvelcharapp.R
import com.example.marvelcharapp.databinding.FragmentDetailBinding
import com.example.marvelcharapp.presentation.base.ErrorUI
import com.example.marvelcharapp.presentation.main.model.CharacterUIModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val bundle = arguments
        val characterId = bundle?.getInt("characterID")
        if (characterId != null) {
            detailViewModel.getCharacter(characterId.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        showLoader(true)
    }

    private fun setupObservers() {
        detailViewModel.character.observe(viewLifecycleOwner, Observer {
            updateView(it)
        })
        detailViewModel.error.observe(viewLifecycleOwner, Observer {
            mapError(it)
        })
        detailViewModel.loading.observe(viewLifecycleOwner, Observer {
            showLoader(it)
        })
    }

    private fun mapError(error: ErrorUI) {
        when (error) {
            is ErrorUI.ConnectionError -> binding.detailErrorView.apply {
                root.visibility = View.VISIBLE
                //root.errorText.text = getString(R.string.connection_error)
            }
            else -> binding.detailErrorView.apply {
                root.visibility = View.VISIBLE
                //root.errorText.text = error.message
            }
        }
    }

    private fun updateView(character: CharacterUIModel) {
        binding.nameTextView.text = character.name
        binding.descriptionTextView.text = character.description

        character.image?.run {
            Picasso.with(context)
                .load(character.image.getBigImageUrl())
                .error(R.drawable.ic_baseline_error_24)
                .into(binding.posterImageView)
        }
    }

    private fun showLoader(show: Boolean) {
        if (show)
            binding.detailProgressBar.visibility = View.VISIBLE
        else
            binding.detailProgressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}