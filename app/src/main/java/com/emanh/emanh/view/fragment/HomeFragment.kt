package com.emanh.emanh.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.emanh.emanh.databinding.FragmentHomeBinding
import com.emanh.emanh.view.adapter.ItemFriendAdapter
import com.emanh.emanh.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val adapter = ItemFriendAdapter(mutableListOf())
        binding.listItemFriend.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.listItemFriend.adapter = adapter

        homeViewModel.friendsList.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
    }
}