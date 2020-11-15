package com.jorbital.xkcdcviewer.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jorbital.service.data.ComicDto
import com.jorbital.xkcdcviewer.R

class FavoritesAdapter(favorites: List<ComicDto>, private val itemClick: (Int) -> Unit) :
    RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    var favoritesList = favorites

    class FavoriteViewHolder(val favoriteItemView: FavoriteItemView) : RecyclerView.ViewHolder(favoriteItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.favorite_item_view,
            parent,
            false
        ) as FavoriteItemView
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.favoriteItemView.bind(favoritesList[position], itemClick)
    }

    override fun getItemCount(): Int {
        return favoritesList.count()
    }
}