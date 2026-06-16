package com.worldcup.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.worldcup.app.data.local.entity.WorldCupTeamEntity

@Dao
interface WorldCupTeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<WorldCupTeamEntity>)

    @Query("SELECT * FROM world_cup_teams")
    suspend fun getAllTeams(): List<WorldCupTeamEntity>

    @Query("SELECT * FROM world_cup_teams WHERE name = :name LIMIT 1")
    suspend fun getTeamByName(name: String): WorldCupTeamEntity?

    @Query("DELETE FROM world_cup_teams")
    suspend fun clearTeams()
}
