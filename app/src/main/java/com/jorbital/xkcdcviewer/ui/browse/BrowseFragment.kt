package com.jorbital.xkcdcviewer.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        observe(viewModel.selectedComic){
            Timber.d("selected comic is $it")
            populateComicView(it)
        }
        viewModel.getLatestComic()
    }

    private fun populateComicView(comic: ComicDto){
        binding.comicView.populateView(comic)
    }
}