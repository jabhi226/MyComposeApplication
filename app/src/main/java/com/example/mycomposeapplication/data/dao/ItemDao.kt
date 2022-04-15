package com.example.mycomposeapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mycomposeapplication.data.entity.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert
    suspend fun insertItem(item: Item)

    @Query ("SELECT * FROM item")
    fun getItem(): Flow<Item>

}