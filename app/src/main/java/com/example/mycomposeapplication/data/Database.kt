package com.example.mycomposeapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mycomposeapplication.data.dao.ItemDao
import com.example.mycomposeapplication.data.entity.Item

@Database(
    entities = [Item::class],
    version = 1
)
abstract class LocalDatabase: RoomDatabase() {

    abstract val itemDao: ItemDao

}