package com.jorbital.xkcdcviewer.ui.favorites

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jorbital.service.data.ComicDto
import com.jorbital.xkcdcviewer.R
import com.jorbital.xkcdcviewer.databinding.FavoritesFragmentBinding
import com.jorbital.xkcdcviewer.extensions.observe
import com.jorbital.xkcdcviewer.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.favorites_fragment) {
    private val binding by viewBinding(FavoritesFragmentBinding::bind)
    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.favorites) {
            binding.loading.isVisible = false
            setupList(it)
        }
        observe(viewModel.emptyFavorites) {
            binding.loading.isVisible = false
            binding.favoritesRv.isVisible = false
            binding.emptyFavorites.isVisible = true
        }
    }

    private fun setupList(favorites: List<ComicDto>) {
        binding.favoritesRv.apply {
            if (this.adapter == null) {
                hasFixedSize()
                layoutManager = LinearLayoutManager(activity)
                adapter = FavoritesAdapter(favorites = favorites, itemClick = favoriteClicked)
            }
        }
    }

    private val favoriteClicked: (Int) -> Unit = { Toast.makeText(requireContext(), "You did it", Toast.LENGTH_SHORT).show() }
}