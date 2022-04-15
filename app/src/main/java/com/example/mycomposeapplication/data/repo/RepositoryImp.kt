package com.example.mycomposeapplication.data.repo

import com.example.mycomposeapplication.data.dao.ItemDao
import com.example.mycomposeapplication.data.entity.Item
import kotlinx.coroutines.flow.Flow

class RepositoryImp(
    val itemDao: ItemDao
): Repository {
    override suspend fun insertItem(item: Item) {
        itemDao.insertItem(item)
    }

    override fun getItem(): Flow<Item> {
        return itemDao.getItem()
    }
}