package com.test.shaadoow.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.shaadoow.R
import com.test.shaadoow.adapter.VerticalAdapter
import com.test.shaadoow.databinding.ActivityMainBinding
import com.test.shaadoow.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.baseListView.layoutManager = LinearLayoutManager(this@MainActivity)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(MainViewModel::class.java)

        // make adapter that will handle data. done
        val adapter = VerticalAdapter()
        binding.baseListView.adapter = adapter

    }
}