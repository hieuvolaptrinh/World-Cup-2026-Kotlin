package com.worldcup.app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.worldcup.app.data.local.dao.WorldCupMatchDao
import com.worldcup.app.data.local.dao.WorldCupTeamDao
import com.worldcup.app.data.local.entity.WorldCupMatchEntity
import com.worldcup.app.data.local.entity.WorldCupTeamEntity
import com.worldcup.app.data.local.converter.Converters
import com.worldcup.app.data.local.dao.LoginPasswordDao
import com.worldcup.app.data.local.entity.LoginPassword

@Database(
    entities = [LoginPassword::class,
        WorldCupMatchEntity::class,
        WorldCupTeamEntity::class],
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


    abstract fun worldCupMatchDao(): WorldCupMatchDao

    abstract fun worldCupTeamDao(): WorldCupTeamDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "world_cup_database"
                )
                    // Dev thì dùng được. App release thật thì nên viết Migration.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
