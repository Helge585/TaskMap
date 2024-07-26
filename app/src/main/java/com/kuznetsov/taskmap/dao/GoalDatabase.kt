package com.kuznetsov.taskmap.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kuznetsov.taskmap.entity.MainGoal
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.entity.SubGoal

@Database(entities = [MainGoal::class, SubGoal::class, Step::class], version = 2, exportSchema = false)
abstract class GoalDatabase : RoomDatabase() {

    abstract val mainGoalDao: MainGoalDao
    abstract val subGoalDao: SubGoalDao
    abstract val stepDao: StepDao

    companion object {
        @Volatile
        private var INSTANCE: GoalDatabase? = null

        private val MIGRATION_1_2 = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE step_table ADD COLUMN start_result INTEGER NOT NULL")
            }
        }

        fun getInstance(context: Context) : GoalDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GoalDatabase::class.java,
                        "goal_database"
                    ).addMigrations(MIGRATION_1_2).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}