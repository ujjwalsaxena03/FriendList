package com.example.simplefriendsapp.repository

import com.example.simplefriendsapp.api.ApiService
import com.example.simplefriendsapp.api.ApiStates
import com.example.simplefriendsapp.datamodels.FriendList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class FriendRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getFriends(): Flow<ApiStates<FriendList>> {
        return flow {
            try {
                val friendList = apiService.getFriends()
                emit(ApiStates.Success(friendList))
            } catch (e: IOException) {
                emit(ApiStates.Error("Network error: ${e.message}"))
            } catch (e: Exception) {
                emit(ApiStates.Error("An error occurred: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }
}