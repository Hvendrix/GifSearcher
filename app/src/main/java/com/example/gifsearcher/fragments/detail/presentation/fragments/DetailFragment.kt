package com.example.gifsearcher.fragments.detail.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gifsearcher.MainActivity
import com.example.gifsearcher.R
import com.example.gifsearcher.databinding.FragmentDetailBinding
import com.example.gifsearcher.fragments.detail.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel
            by hiltNavGraphViewModels(R.id.DetailFragment)
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {

            launch {
                viewModel.getGifById(args.gifId)
            }

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.gif.collect{gif ->
                    if(gif!=null) {
                        Glide.with(this@DetailFragment)
                            .asGif()
                            .load(gif.images.original.url)
                            .fallback(R.drawable.gif)
                            .into(binding.imageGif)

                        binding.infoText.text  = buildString {

                            gif.title.takeIf { it.isNotBlank() }?.let { title ->
                                append("Название:")
                                append("\n")
                                append(title)

                            }

                            gif.username.takeIf { it.isNotBlank()}?.let { username ->
                                append("\n\n")
                                append("Автор:")
                                append("\n")
                                append(username)
                            }


                            gif.datetime.takeIf { it.isNotBlank() }?.let { datetime ->
                                append("\n\n")
                                append("Дата и время загрузки:")
                                append("\n")
                                append(datetime)

                            }

                            gif.source.takeIf { it.isNotBlank() }?.let { source ->
                                append("\n\n")
                                append("Источник:")
                                append("\n")
                                append(source)
                            }
                        }
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).searchMenu?.apply {
            setQuery("", false);
            clearFocus()
            isIconified = true
//            visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}