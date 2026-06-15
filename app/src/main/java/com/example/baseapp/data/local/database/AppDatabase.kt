package com.example.baseapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.baseapp.data.local.converter.Converters
import com.example.baseapp.data.local.dao.LoginPasswordDao
import com.example.baseapp.data.local.dao.MatchDao
import com.example.baseapp.data.local.dao.StandingDao
import com.example.baseapp.data.local.dao.TeamDao
import com.example.baseapp.data.local.entity.LoginPassword
import com.example.baseapp.data.local.entity.Match
import com.example.baseapp.data.local.entity.Standing
import com.example.baseapp.data.local.entity.Team

@Database(
        entities = [LoginPassword::class, Team::class, Match::class, Standing::class],
        version = 2,
        exportSchema = true,
        //    autoMigrations = [
        //        AutoMigration(
        //            from = 1,
        //            to = 2
        //        ),
        //    ]
        )
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginPasswordDao
    abstract fun teamDao(): TeamDao
    abstract fun matchDao(): MatchDao
    abstract fun standingDao(): StandingDao
}
