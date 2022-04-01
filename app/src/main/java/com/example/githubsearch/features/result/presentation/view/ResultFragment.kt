package com.example.githubsearch.features.result.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.githubsearch.MainViewModel
import com.example.githubsearch.core.utility.initRecyclerViewWithoutLineDecoration
import com.example.githubsearch.databinding.FragmentResultBinding
import com.example.githubsearch.features.result.presentation.adapter.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchResultAdapter: SearchResultAdapter
    private val args: ResultFragmentArgs by navArgs()
    private var query = ""
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentResultBinding.inflate(layoutInflater, container, false)
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()

        query = args.query

        viewModel.nextPage.observe(
            viewLifecycleOwner
        ) {
            it?.let { page ->
                viewModel.performSearch(query, page)
            }
        }

        enablePrevButton(currentPage)

        binding.nextButton.setOnClickListener {
            viewModel.setPage(currentPage + 1)
            currentPage += 1
            enablePrevButton(currentPage)
        }

        binding.prevButton.setOnClickListener {
            if (currentPage > 1) {
                viewModel.setPage(currentPage - 1)
                currentPage -= 1
                enablePrevButton(currentPage)
            }
        }
    }

    private fun setUpAdapter() {
        val viewModel = binding.mainViewModel
        if (viewModel != null) {
            searchResultAdapter = SearchResultAdapter()

            context?.let {
                binding.resultRecyclerView.initRecyclerViewWithoutLineDecoration(it)
            }
            binding.resultRecyclerView.adapter = searchResultAdapter
        }
    }

    private fun enablePrevButton(currentPage: Int) {
        binding.prevButton.isEnabled = currentPage != 1
    }
}
