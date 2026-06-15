package com.example.baseapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseapp.data.local.entity.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getAllTeams(): Flow<List<Team>>

    @Query("SELECT * FROM teams WHERE id = :teamId")
    suspend fun getTeamById(teamId: String): Team?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<Team>)

    @Query("DELETE FROM teams")
    suspend fun deleteAll()
}
