package com.kuznetsov.taskmap

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MainGoal::class, SubGoal::class, Step::class], version = 1, exportSchema = false)
abstract class GoalDatabase : RoomDatabase() {

    abstract val mainGoalDao: MainGoalDao
    abstract val subGoalDao: SubGoalDao
    abstract val stepDao: StepDao

    companion object {
        @Volatile
        private var INSTANCE: GoalDatabase? = null

        fun getInstance(context: Context) : GoalDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GoalDatabase::class.java,
                        "goal_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}