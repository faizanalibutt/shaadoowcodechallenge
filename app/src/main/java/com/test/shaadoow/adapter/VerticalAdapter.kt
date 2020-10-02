package com.test.shaadoow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.shaadoow.AppDelegate
import com.test.shaadoow.R
import com.test.shaadoow.ui.viewmodel.ArtistViewModel
import com.test.shaadoow.ui.viewmodel.MainViewModel
import com.test.shaadoow.util.Utils
import kotlinx.android.synthetic.main.item_horizontal.view.*
import timber.log.Timber

class VerticalAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_RECOMMEND = 0
    private val TYPE_HORIZONTAL = 1
    private val TYPE_FEED = 2
    private val TYPE_VERTICAL = 3

    private val feedsList = Utils.verticalListData

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_RECOMMEND -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
                ViewHolder1(view)
            }
            TYPE_HORIZONTAL -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_horizontal, parent, false)
                HorizontalHolder(view)
            }
            TYPE_FEED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false)
                ViewHolder2(view)
            }
            TYPE_VERTICAL -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(android.R.layout.activity_list_item, parent, false)
                FeedHolder(view)
            }

            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FeedHolder) {
            holder.bindData(feedsList[position - 3], position)
        }
    }

    override fun getItemCount(): Int {
        return feedsList.size + 3
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_RECOMMEND
            1 -> TYPE_HORIZONTAL
            2 -> TYPE_FEED
            else -> TYPE_VERTICAL
        }
    }

    class ViewHolder1(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val view: View = v
        init {
            v.setOnClickListener(this)
            view.findViewById<TextView>(android.R.id.text1)
                .text = view.context.getString(R.string.text_recommend)
        }
        override fun onClick(seeAll: View?) { /* show All artists. */ }
    }

    class HorizontalHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val view: View = v
        init {
            view.horizontalListView.layoutManager = LinearLayoutManager(
                view.context, RecyclerView.HORIZONTAL, false
            )
            val viewModel = ViewModelProvider(
                (view.context as AppCompatActivity), ViewModelProvider.AndroidViewModelFactory
                    .getInstance((view.context as AppCompatActivity).application)
            ).get(ArtistViewModel::class.java)
            val adapter = ArtistsListAdapter{
                viewModel.retry()
            }
            viewModel.artists.observe((view.context as AppCompatActivity), Observer {
                    pagedList -> adapter.submitList(pagedList)
            })
            viewModel.networkState.observe((view.context as AppCompatActivity), Observer {
                    networkState -> adapter.setNetworkState(networkState!!)
            })
            view.horizontalListView.adapter = adapter
        }
    }

    class ViewHolder2(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val view: View = v
        init {
            v.setOnClickListener(this)
            view.findViewById<TextView>(android.R.id.text1)
                .text = view.context.getString(R.string.text_feed)
        }
        override fun onClick(seeAll: View?) { /* show post feeds. */ }
    }

    class FeedHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val view: View = v

        init {
        }

        fun bindData(item: String, position: Int) {
            view.findViewById<TextView>(android.R.id.text1).text = item
        }

    }

}