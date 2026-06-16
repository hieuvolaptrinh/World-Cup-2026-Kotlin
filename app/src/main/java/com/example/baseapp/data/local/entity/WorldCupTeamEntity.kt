package com.worldcup.app.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "world_cup_teams")
data class WorldCupTeamEntity(
    @PrimaryKey
    val name: String,

    @ColumnInfo(name = "name_normalised")
    val nameNormalised: String?,

    val continent: String?,

    @ColumnInfo(name = "flag_icon")
    val flagIcon: String?,

    @ColumnInfo(name = "flag_unicode")
    val flagUnicode: String?,

    @ColumnInfo(name = "fifa_code")
    val fifaCode: String?,

    @ColumnInfo(name = "group_name")
    val groupName: String?,

    val confed: String?
)
