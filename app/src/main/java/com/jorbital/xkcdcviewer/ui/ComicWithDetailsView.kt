package com.jorbital.xkcdcviewer.ui

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import coil.load
import com.jorbital.service.data.ComicDto
import com.jorbital.xkcdcviewer.R
import com.jorbital.xkcdcviewer.databinding.ComicWithDetailsViewBinding
import com.jorbital.xkcdcviewer.extensions.viewBinding
import com.jorbital.xkcdcviewer.util.DateHelper.formattedDate

class ComicWithDetailsView : ConstraintLayout {
    private val binding by viewBinding(ComicWithDetailsViewBinding::bind)

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        LayoutInflater.from(context).inflate(R.layout.comic_with_details_view, this, true)
    }

    private var isFavorite: Boolean = false

    fun populateView(comic: ComicDto, favoriteClicked: (Int) -> Unit) {
        binding.comicNumberAndTitle.text = context.getString(R.string.comic_number_and_title, comic.comicNumber, comic.title)
        binding.comicDate.text = formattedDate(comic.year, comic.month, comic.day)
        binding.comicAltText.text = comic.altText
        binding.comicImage.load(comic.imageUrl)
        binding.comicImage.contentDescription = comic.transcript

        isFavorite = comic.favorite

        binding.addFavoriteButton.isVisible = true
        binding.shareComicButton.isVisible = true

        binding.addFavoriteButton.setImageResource(if (isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_outline)
        binding.addFavoriteButton.setOnClickListener {
            binding.addFavoriteButton.setImageResource(if (isFavorite) R.drawable.ic_favorite_outline else R.drawable.ic_favorite_filled)
            isFavorite = !isFavorite
            favoriteClicked(comic.comicNumber)
        }

        binding.shareComicButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://xkcd.com/${comic.comicNumber}/")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }
}