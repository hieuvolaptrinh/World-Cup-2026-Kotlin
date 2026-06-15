package com.example.baseapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey
    val id: String,
    val name: String,
    val flagUrl: String,
    val groupName: String? = null
)
