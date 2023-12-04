package com.example.marvelcharapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharapp.R
import com.example.marvelcharapp.databinding.FragmentMainBinding
import com.example.marvelcharapp.presentation.base.ErrorUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MainAdapter.OnItemClicked {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter = MainAdapter(mutableListOf(), this)
    private var totalOffset = 0
    private val OFFSET = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
        mainViewModel.getCharacterList(0)
    }

    private fun setupObservers() {
        mainViewModel.characterList.observe(viewLifecycleOwner, Observer {
            mainAdapter.updateList(it)
        })

        mainViewModel.error.observe(viewLifecycleOwner, Observer {
            mapError(it)
        })

        mainViewModel.loading.observe(viewLifecycleOwner, Observer {
            showLoader(it)
        })
    }

    private fun setupView() {
        with(binding.mainRecycler) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter =  mainAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        setUpScrollLoader(layoutManager as GridLayoutManager)
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
        binding.errorView.reloadButton.setOnClickListener {
            mainViewModel.getCharacterList(0)
        }
    }

    private fun setUpScrollLoader(layoutManager: GridLayoutManager) {
        val visibleItemCount = layoutManager.childCount
        val scrolledItems = layoutManager.findFirstCompletelyVisibleItemPosition()
        val totalCount = layoutManager.itemCount

        if (!mainViewModel.loading.value!!) {
            if ((visibleItemCount + scrolledItems) >= totalCount) {
                totalOffset += OFFSET
                mainViewModel.getCharacterList(totalOffset)
            }
        }
    }

    private fun showLoader(show: Boolean) {
        if (show)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

    private fun mapError(error: ErrorUI) {
        when (error) {
            is ErrorUI.ConnectionError -> binding.errorView.apply {
                root.visibility = View.VISIBLE
                //root.errorText.text = getString(R.string.connection_error)
            }
            else -> binding.errorView.apply {
                root.visibility = View.VISIBLE
                //root.errorText.text = error.message
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemClicked(character: CharacterUIModel) {
        val bundle = bundleOf("characterID" to character.id)
        NavHostFragment.findNavController(this).navigate(R.id.action_MainFragment_to_DetailFragment, bundle)
    }

}