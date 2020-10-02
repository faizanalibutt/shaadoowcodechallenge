package com.test.shaadoow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.shaadoow.R
import com.test.shaadoow.databinding.ItemArtistBinding
import com.test.shaadoow.databinding.ItemNetworkStateBinding
import com.test.shaadoow.model.ArtistData
import com.test.shaadoow.ui.viewmodel.ArtistDataViewModel
import com.test.shaadoow.ui.viewmodel.NetworkStateViewModel
import com.test.shaadoow.util.Constants.DIFF_CALL
import com.test.shaadoow.util.NetworkState

class ArtistsListAdapter(private val retryCallback: () -> Unit) :
        PagedListAdapter<ArtistData, RecyclerView.ViewHolder>(DIFF_CALL) {

    private val TYPE_PROGRESS = 0
    private val TYPE_ITEM = 1
    var onItemClick: ((ArtistData) -> Unit)? = null

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_ITEM -> {
                val binding: ItemArtistBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.item_artist, parent, false)
                ViewHolder1(binding)
            }
            TYPE_PROGRESS -> {
                val binding: ItemNetworkStateBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.item_network_state, parent, false)
                ViewHolder2(binding, retryCallback)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder1) {
            holder.bind(getItem(position)!!, position)
        } else if (holder is ViewHolder2) {
            holder.bind(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    inner class ViewHolder1(private val binding: ItemArtistBinding)
        : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ArtistDataViewModel()
        fun bind(artist: ArtistData, position: Int) {
            viewModel.bind(artist, position)
            binding.artistImage.setOnClickListener {
                onItemClick?.invoke(artist)
            }
            binding.viewModel = viewModel
        }
    }

    internal class ViewHolder2(private val binding: ItemNetworkStateBinding,
                      private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = NetworkStateViewModel()
        init {
            binding.retry.setOnClickListener {
                retryCallback.invoke()
            }
        }
        fun bind(networkState: NetworkState?) {
            viewModel.bindView(networkState)
            binding.viewModel = viewModel
        }
    }

}