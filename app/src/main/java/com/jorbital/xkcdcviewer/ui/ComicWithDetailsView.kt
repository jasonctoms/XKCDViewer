package com.jorbital.xkcdcviewer.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.jorbital.service.data.ComicDto
import com.jorbital.xkcdcviewer.R
import com.jorbital.xkcdcviewer.databinding.ComicWithDetailsViewBinding
import com.jorbital.xkcdcviewer.extensions.viewBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ComicWithDetailsView : ConstraintLayout {
    private val binding by viewBinding(ComicWithDetailsViewBinding::bind)

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        LayoutInflater.from(context).inflate(R.layout.comic_with_details_view, this, true)
    }

    fun populateView(comic: ComicDto) {
        binding.comicNumberAndTitle.text = context.getString(R.string.comic_number_and_title, comic.comicNumber, comic.title)
        binding.comicDate.text = formattedDate(comic.year, comic.month, comic.day)
        binding.comicAltText.text = comic.altText
        binding.comicImage.load(comic.imageUrl)
        binding.comicImage.contentDescription = comic.transcript
    }

    private fun formattedDate(year: Int, month: Int, day: Int): String {
        val date = LocalDate.parse("$year-$month-$day")
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    }
}