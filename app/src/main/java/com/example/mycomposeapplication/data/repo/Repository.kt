package com.example.mycomposeapplication.data.repo

import androidx.room.Insert
import androidx.room.Query
import com.example.mycomposeapplication.data.entity.Item
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insertItem(item: Item)

    fun getItem(): Flow<Item>
}