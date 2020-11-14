package com.jorbital.xkcdcviewer.ui.browse

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.jorbital.service.data.ComicDto
import com.jorbital.xkcdcviewer.R
import com.jorbital.xkcdcviewer.databinding.BrowseFragmentBinding
import com.jorbital.xkcdcviewer.extensions.observe
import com.jorbital.xkcdcviewer.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class BrowseFragment : Fragment(R.layout.browse_fragment) {

    private val binding by viewBinding(BrowseFragmentBinding::bind)
    private val viewModel: BrowseViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.selectedComic) {
            Timber.d("selected comic is $it")
            populateComicView(it)
            binding.loading.isVisible = false
        }
        observe(viewModel.loading) {
            binding.loading.isVisible = true
        }
        viewModel.getLatestComic()
        binding.nextButton.setOnClickListener { viewModel.getNextComic() }
        binding.previousButton.setOnClickListener { viewModel.getPreviousComic() }
    }

    private fun populateComicView(comic: ComicDto) {
        binding.nextButton.isEnabled = viewModel.currentComicNumber < viewModel.latestComicNumber
        binding.previousButton.isEnabled = viewModel.currentComicNumber >= 1

        binding.comicView.populateView(comic)
    }
}