package com.example.baseapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseapp.data.local.entity.Standing
import kotlinx.coroutines.flow.Flow

@Dao
interface StandingDao {
    @Query("SELECT * FROM standings WHERE groupName = :groupName ORDER BY position ASC")
    fun getStandingsByGroup(groupName: String): Flow<List<Standing>>

    @Query("SELECT DISTINCT groupName FROM standings ORDER BY groupName ASC")
    suspend fun getAllGroups(): List<String>

    @Query("SELECT * FROM standings ORDER BY groupName ASC, position ASC")
    fun getAllStandings(): Flow<List<Standing>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStandings(standings: List<Standing>)

    @Query("DELETE FROM standings")
    suspend fun deleteAll()
}
