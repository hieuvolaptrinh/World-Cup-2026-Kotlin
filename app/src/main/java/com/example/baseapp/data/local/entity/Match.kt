package com.example.baseapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey
    val id: String,
    val homeTeamId: String,
    val awayTeamId: String,
    val homeScore: Int?,
    val awayScore: Int?,
    val matchDateTime: Long, // timestamp in milliseconds
    val status: MatchStatus,
    val round: String, // "Group Stage Round 1", "Round of 16", etc.
    val stadium: String? = null
)

enum class MatchStatus {
    SCHEDULED,  // Chưa diễn ra
    LIVE,       // Đang diễn ra
    FINISHED    // Đã kết thúc (FT)
}
