package com.test.shaadoow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.shaadoow.R
import com.test.shaadoow.util.Utils

class HorizontalAdapter(private val retryCallback: () -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_STATE = 0
    private val TYPE_ARTIST = 1

    private val artistsList = Utils.horizontalListData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ARTIST -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.activity_list_item, parent, false)
                ArtistHolder(view)
            }
            /*TYPE_STATE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_horizontal, parent, false)
                VerticalAdapter.HorizontalHolder(view)
            }*/

            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArtistHolder)
            holder.bindData(artistsList[position], position)
    }

    override fun getItemCount(): Int = artistsList.size

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        /*return when (position) {
            0 -> TYPE_ARTIST
            1 -> TYPE_HORIZONTAL
            2 -> TYPE_FEED
            else -> TYPE_VERTICAL
        }*/
        return TYPE_ARTIST
    }

    class ArtistHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view: View = v
        fun bindData(item: Int, position: Int) {
            Glide.with(view.context)
                .load(item)
                .centerCrop()
                .apply(RequestOptions().override(40, 60))
                .into(view.findViewById<ImageView>(android.R.id.icon))
        }
    }


}