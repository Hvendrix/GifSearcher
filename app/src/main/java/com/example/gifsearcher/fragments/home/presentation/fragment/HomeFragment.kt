package com.example.gifsearcher.fragments.home.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gifsearcher.R
import com.example.gifsearcher.fragments.home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.gifsearcher.MainActivity
import com.example.gifsearcher.databinding.FragmentHomeBinding
import com.example.gifsearcher.fragments.home.presentation.adapter.GifsAdapter
import com.example.gifsearcher.fragments.home.presentation.viewholder.GifMarginItemDecoration
import com.example.gifsearcher.fragments.home.presentation.viewholder.OnClickGif
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment(), OnClickGif {

    private val viewModel: HomeViewModel
            by hiltNavGraphViewModels(R.id.HomeFragment)
    private var _binding: FragmentHomeBinding? = null


    private var searchJob: Job? = null

    private val binding get() = _binding!!

    var adapter: GifsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GifsAdapter(this)
        binding.adapterGifs.adapter = adapter

        binding.adapterGifs.addItemDecoration(
            GifMarginItemDecoration(resources.getDimensionPixelSize(R.dimen.gif_margin))
        )

        binding.btnShowMore.setOnClickListener {
            (activity as MainActivity).viewModel.searchQuery.value.let { search ->
                binding.btnShowMore.visibility = View.GONE
                if (search.isBlank()) {
                    viewModel.getGifs()
                } else {
                    adapter?.submitList(listOf())
                    viewModel.searchGifs(search)
                }
            }

        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    (activity as MainActivity).viewModel.searchQuery.collect { search ->
                        searchJob?.cancel()
                        binding.btnShowMore.visibility = View.GONE
                        searchJob = launch {
                            if (search.isBlank()) {
                                viewModel.getGifs()
                            } else {
                                delay(500)
                                viewModel.clearGifs()
                                viewModel.searchGifs(search)
                            }
                        }
                    }
                }
                launch {
                    (activity as MainActivity).viewModel.reloadGifs.collect{
                        if(it){
                            viewModel.clearGifs()
                            viewModel.getGifs()
                            (activity as MainActivity).viewModel.needReloadGifs(false)
                        }

                    }
                }
            }

        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.gifs.collect { gifs ->
                        if (gifs != null) {
                            adapter?.let { adapter ->
                                adapter.submitList(gifs.toList())
                            }


                        }
                        if (gifs.isNullOrEmpty()) {
                            binding.textNotFound.visibility = View.VISIBLE
                            binding.btnShowMore.visibility = View.GONE
                        } else {
                            binding.textNotFound.visibility = View.GONE
                            binding.btnShowMore.visibility = View.VISIBLE
                        }

                    }
                }


            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(gifId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(gifId)
        findNavController().navigate(action)
    }
}