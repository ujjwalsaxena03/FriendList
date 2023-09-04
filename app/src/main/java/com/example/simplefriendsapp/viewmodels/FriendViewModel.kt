package com.example.simplefriendsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplefriendsapp.api.ApiStates
import com.example.simplefriendsapp.datamodels.FriendList
import com.example.simplefriendsapp.repository.FriendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(private val repository: FriendRepository) : ViewModel() {

    private val _friendList = MutableStateFlow<ApiStates<FriendList>>(ApiStates.Loading)
    val friendList: StateFlow<ApiStates<FriendList>> = _friendList

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                val friendList = repository.getFriends()
                friendList.collect { friendList ->
                    _friendList.emit(friendList)
                }
            } catch (e: Exception) {
                _friendList.emit(ApiStates.Error("Failed to fetch items: ${e.message}"))
            }
        }
    }
}