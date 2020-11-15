package com.jorbital.xkcdcviewer.ui.favorites

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import coil.load
import com.jorbital.service.data.ComicDto
import com.jorbital.xkcdcviewer.R
import com.jorbital.xkcdcviewer.databinding.FavoriteItemViewBinding
import com.jorbital.xkcdcviewer.extensions.viewBinding
import com.jorbital.xkcdcviewer.util.DateHelper
import com.jorbital.xkcdcviewer.util.DateHelper.formattedDate

class FavoriteItemView : CardView {

    private val binding by viewBinding(FavoriteItemViewBinding::bind)

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    fun bind(comic: ComicDto, itemClicked: (Int) -> Unit) {
        setOnClickListener { itemClicked(comic.comicNumber) }

        binding.comicNumberAndTitle.text = context.getString(R.string.comic_number_and_title, comic.comicNumber, comic.title)
        binding.comicDate.text = formattedDate(comic.year, comic.month, comic.day)
        binding.comicAltText.text = comic.altText
        binding.comicImage.load(comic.imageUrl)
        binding.comicImage.contentDescription = comic.transcript
    }
}