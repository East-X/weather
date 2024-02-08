package com.eastx7.weather

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.eastx7.weather.data.db.DbDay
import com.eastx7.weather.data.db.DbDayDao
import com.eastx7.weather.utilities.Constants.DATABASE_NAME
import com.eastx7.weather.utilities.Converters

@Database(
    entities = [
        DbDay::class
    ],
    version = 1,
    exportSchema = true,
//    autoMigrations = [
//        AutoMigration(from = 5, to = 6, spec = AppDatabase.AutoMigration5to6::class)
//    ]
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dbDayDao(): DbDayDao

//    @RenameColumn(tableName = "days", fromColumnName = "desc", toColumnName = "description")
//    class AutoMigration5to6 : AutoMigrationSpec

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
