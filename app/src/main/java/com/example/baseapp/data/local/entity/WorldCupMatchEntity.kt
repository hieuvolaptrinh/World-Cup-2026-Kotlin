package com.worldcup.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "world_cup_matches")
data class WorldCupMatchEntity(

    @PrimaryKey val id: String,

    val round: String,
    val date: String,

    val time: String,

    @ColumnInfo(name = "kickoff_millis")
    val kickoffMillis: Long?,

    @ColumnInfo(name = "local_match_date")
    val localMatchDate: String?,

    @ColumnInfo(name = "home_team_name")
    val homeTeamName: String,

    @ColumnInfo(name = "home_team_flag")
    val homeTeamFlag: String,

    @ColumnInfo(name = "away_team_name")
    val awayTeamName: String,

    @ColumnInfo(name = "away_team_flag")
    val awayTeamFlag: String,

    @ColumnInfo(name = "home_score")
    val homeScore: Int?,

    @ColumnInfo(name = "away_score")
    val awayScore: Int?,

    @ColumnInfo(name = "group_name")
    val groupName: String?,

    val stadium: String?,

    @ColumnInfo(name = "last_updated_at")
    val lastUpdatedAt: Long

)
