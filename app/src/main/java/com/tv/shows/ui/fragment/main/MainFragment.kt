package com.tv.shows.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.tv.shows.databinding.FragmentMainBinding
import com.tv.shows.ui.adapter.TvShowAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val GRID_ROWS = 3

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val adapter = TvShowAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, GRID_ROWS)

        viewModel.shows.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.hasInternetConnection.observe(viewLifecycleOwner) { hasInternetConnection ->
            if (hasInternetConnection) {
                binding.imageNoConnection.visibility = View.GONE
                binding.textNoConnection.visibility = View.GONE
            } else {
                binding.imageNoConnection.visibility = View.VISIBLE
                binding.textNoConnection.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}
