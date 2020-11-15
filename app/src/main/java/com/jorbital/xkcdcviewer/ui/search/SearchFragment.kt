package com.jorbital.xkcdcviewer.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.jorbital.xkcdcviewer.R
import com.jorbital.xkcdcviewer.databinding.SearchFragmentBinding
import com.jorbital.xkcdcviewer.extensions.observe
import com.jorbital.xkcdcviewer.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : Fragment(R.layout.search_fragment) {

    private val binding by viewBinding(SearchFragmentBinding::bind)
    private val viewModel: SearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.selectedComic) {
            Timber.d("selected comic is $it")
            binding.comicView.populateView(it)
            binding.loading.isVisible = false
        }
        observe(viewModel.percentMatch) {
            binding.percentMatch.text = getString(R.string.percent_match, it.toString())
        }
        observe(viewModel.loading) {
            binding.loading.isVisible = true
        }

        binding.searchButton.setOnClickListener {
            val text = binding.searchField.editText?.text
            if (!text.isNullOrBlank()){
                viewModel.performSearch(text.toString())
            }
        }
    }
}