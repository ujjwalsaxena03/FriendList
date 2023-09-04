package com.example.simplefriendsapp.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplefriendsapp.adapters.FriendAdapter
import com.example.simplefriendsapp.api.ApiStates
import com.example.simplefriendsapp.databinding.ActivityMainBinding
import com.example.simplefriendsapp.viewmodels.FriendViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: FriendViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val friendAdapter = FriendAdapter { selectedItem ->
            Toast.makeText(this, "You chose ${selectedItem.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvFriendList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = friendAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.friendList.collect { ApiStateResult ->
                when (ApiStateResult) {
                    is ApiStates.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ApiStates.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val friendList = ApiStateResult.data
                        friendAdapter.submitList(friendList)
                    }

                    is ApiStates.Error -> {
                        binding.progressBar.visibility = View.GONE
                        val errorMessage = ApiStateResult.message
                        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}