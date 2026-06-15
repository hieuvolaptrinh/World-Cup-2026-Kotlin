package com.example.baseapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baseapp.data.local.entity.Match
import com.example.baseapp.data.local.entity.MatchStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches ORDER BY matchDateTime ASC")
    fun getAllMatches(): Flow<List<Match>>

    @Query("SELECT * FROM matches WHERE round = :round ORDER BY matchDateTime ASC")
    fun getMatchesByRound(round: String): Flow<List<Match>>

    @Query("SELECT * FROM matches WHERE status = :status ORDER BY matchDateTime ASC")
    fun getMatchesByStatus(status: MatchStatus): Flow<List<Match>>

    @Query("SELECT DISTINCT round FROM matches ORDER BY matchDateTime ASC")
    suspend fun getAllRounds(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<Match>)

    @Query("DELETE FROM matches")
    suspend fun deleteAll()
}
