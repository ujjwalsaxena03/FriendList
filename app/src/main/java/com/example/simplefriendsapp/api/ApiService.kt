package com.example.simplefriendsapp.api

import com.example.simplefriendsapp.datamodels.FriendList
import retrofit2.http.GET

interface ApiService {
    @GET("b/B9PJ")
    suspend fun getFriends(): FriendList

}