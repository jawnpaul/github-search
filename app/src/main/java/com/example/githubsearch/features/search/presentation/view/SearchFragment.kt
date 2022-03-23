package com.example.githubsearch.features.search.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.githubsearch.MainViewModel
import com.example.githubsearch.R
import com.example.githubsearch.core.utility.afterTextChanged
import com.example.githubsearch.core.utility.safeNavigate
import com.example.githubsearch.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginView.observe(viewLifecycleOwner, {
            if (it?.response != null) {

                // Make API call to github
                viewModel.performSearch(binding.loginTextInputEditText.text.toString(), 1)

                val action = SearchFragmentDirections.actionSearchFragmentToResultFragment()
                findNavController().safeNavigate(action)

                // Unset value to handle back navigation
                it.response = null
            }

            if (it?.error != null) {
                Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
            }
        })

        binding.submitButton.setOnClickListener {
            viewModel.search(binding.loginTextInputEditText.text.toString())
        }

        binding.loginTextInputEditText.afterTextChanged {
            binding.submitButton.isEnabled = it.isNotEmpty()
        }

    }
}