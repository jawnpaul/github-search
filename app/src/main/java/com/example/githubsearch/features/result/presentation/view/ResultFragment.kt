package com.example.githubsearch.features.result.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.githubsearch.MainViewModel
import com.example.githubsearch.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
    }

    private fun setUpAdapter(){
        val viewModel = binding.mainViewModel
        if (viewModel != null){
            searchResultAdapter = SearchResultAdapter()

            context?.let {
                binding.resultRecyclerView.initRecyclerViewWithoutLineDecoration(it)
            }
            binding.resultRecyclerView.adapter = searchResultAdapter
        }
    }

}