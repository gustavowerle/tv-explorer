package com.tv.shows.ui.fragment.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.tv.shows.databinding.FragmentDetailBinding
import com.tv.shows.ui.helper.ImageLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)?.supportActionBar?.hide()

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel.setShow(args.show)

        viewModel.show.observe(viewLifecycleOwner) { show ->
            show.image?.original?.let { url ->
                ImageLoader().load(url).into(binding.imageView)
            }
            show.genres?.forEach { genre ->
                binding.chipGroupGenres.addView(createTagChip(genre))
            }
            binding.textFullTitle.text = show.name
            binding.textStoryLine.text = Html.fromHtml(show.summary)

            show.rating?.average?.let {
                binding.textRating.visibility = View.VISIBLE
                binding.ratingBar.visibility = View.VISIBLE
                binding.textRating.text = it.toString()
                binding.ratingBar.rating = it / 2
            } ?: run {
                binding.textRating.visibility = View.GONE
                binding.ratingBar.visibility = View.GONE
            }
        }

        return binding.root
    }

    private fun createTagChip(chipName: String) = Chip(context).apply {
        text = chipName
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
}
