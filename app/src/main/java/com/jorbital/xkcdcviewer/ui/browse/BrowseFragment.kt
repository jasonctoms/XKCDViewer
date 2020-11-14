package com.jorbital.xkcdcviewer.ui.browse

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.jorbital.service.data.ComicDto
import com.jorbital.xkcdcviewer.R
import com.jorbital.xkcdcviewer.databinding.BrowseFragmentBinding
import com.jorbital.xkcdcviewer.extensions.afterTextChanged
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
        binding.goToButton.setOnClickListener { openGoToDialog() }
    }

    private fun openGoToDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.go_to_specific_comic))
        val dialogLayout = layoutInflater.inflate(R.layout.go_to_comic_dialog, null)
        val inputLayout = dialogLayout.findViewById<TextInputLayout>(R.id.text_field)
        inputLayout.editText?.afterTextChanged {
            if (isValidComicNumber(it) || it.isBlank()) {
                inputLayout.error = null
            } else {
                inputLayout.error = getString(R.string.enter_a_valid_number)
            }
        }
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { _, _ ->
            val text = inputLayout.editText?.text
            if (inputLayout.error == null && !text.isNullOrBlank()) {
                viewModel.getSpecificComic(text.toString().toInt())
            } else if (!text.isNullOrBlank()) {
                Toast.makeText(context, getString(R.string.enter_a_valid_number), Toast.LENGTH_SHORT).show()
            }
        }
        builder.show()
    }

    private fun isValidComicNumber(number: String): Boolean {
        number.toIntOrNull()?.let {
            if (it > 0 && it <= viewModel.latestComicNumber) return true
        }
        return false
    }

    private fun populateComicView(comic: ComicDto) {
        binding.nextButton.isEnabled = viewModel.currentComicNumber < viewModel.latestComicNumber
        binding.previousButton.isEnabled = viewModel.currentComicNumber > 1

        binding.comicView.populateView(comic)
    }
}