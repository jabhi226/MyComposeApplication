package com.example.mycomposeapplication.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "item")
data class Item(
    @PrimaryKey val id: Int?,
    var name: String?,
    var details: String?
) {
}