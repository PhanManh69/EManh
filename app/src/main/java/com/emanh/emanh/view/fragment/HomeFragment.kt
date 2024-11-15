package com.emanh.emanh.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emanh.emanh.databinding.FragmentHomeBinding
import com.emanh.emanh.view.adapter.ItemFriendAdapter
import com.emanh.emanh.view.adapter.ItemPostAdapter
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
        val adapterFriend = ItemFriendAdapter(mutableListOf())
        binding.listItemFriend.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.listItemFriend.adapter = adapterFriend

        homeViewModel.friendsList.observe(viewLifecycleOwner, Observer {
            adapterFriend.updateList(it)
        })

        val adapterPost = ItemPostAdapter(mutableListOf(), homeViewModel)
        binding.listItemPost.layoutManager = LinearLayoutManager(context)
        binding.listItemPost.adapter = adapterPost

        homeViewModel.postList.observe(viewLifecycleOwner, Observer {
            adapterPost.updateList(it)
        })
    }
}