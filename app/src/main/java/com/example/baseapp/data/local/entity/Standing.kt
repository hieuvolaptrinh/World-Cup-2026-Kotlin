package com.example.baseapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "standings")
data class Standing(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val teamId: String,
    val groupName: String,
    val matchesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val points: Int,
    val position: Int // 1, 2, 3, 4
)
