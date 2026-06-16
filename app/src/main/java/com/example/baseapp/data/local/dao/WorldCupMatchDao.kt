package com.worldcup.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.worldcup.app.data.local.entity.WorldCupMatchEntity


@Dao
interface WorldCupMatchDao {

    //    nếu đã có thì đè lên dữ liệu cũ
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<WorldCupMatchEntity>)

    @Transaction
    suspend fun replaceMatches(matches: List<WorldCupMatchEntity>) {
        clearMatches()
        insertMatches(matches)
    }

    @Query("SELECT * FROM world_cup_matches ORDER BY kickoff_millis ASC")
    suspend fun getAllMatches(): List<WorldCupMatchEntity>

    @Query(
        """
        SELECT * FROM world_cup_matches
        WHERE round = :round
        ORDER BY kickoff_millis ASC
    """
    )
    suspend fun getMatchesByRound(round: String): List<WorldCupMatchEntity>

    @Query(
        """
        SELECT * FROM world_cup_matches
        WHERE local_match_date = :localDate
        ORDER BY kickoff_millis ASC
    """
    )
    suspend fun getMatchesByLocalDate(localDate: String): List<WorldCupMatchEntity>

    @Query(
        """
        SELECT * FROM world_cup_matches
        ORDER BY
            CASE
                WHEN local_match_date < :yesterday THEN 0
                WHEN local_match_date = :yesterday THEN 1
                WHEN local_match_date = :today THEN 2
                WHEN local_match_date > :today THEN 3
                ELSE 4
            END,
            kickoff_millis ASC
    """
    )
    suspend fun getWidgetMatches(today: String, yesterday: String): List<WorldCupMatchEntity>

    @Query("DELETE FROM world_cup_matches")
    suspend fun clearMatches()
}
