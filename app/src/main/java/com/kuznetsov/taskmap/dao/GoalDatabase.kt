package com.kuznetsov.taskmap.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kuznetsov.taskmap.entity.Increment
import com.kuznetsov.taskmap.entity.MainGoal
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.entity.SubGoal

@Database(entities = [MainGoal::class, SubGoal::class, Step::class, Increment::class],
    version = 3, exportSchema = false)
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

        private val MIGRATION_2_3 = object: Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS increment_table (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "step_id INTEGER NOT NULL," +
                        "increment_value INTEGER DEFAULT 0 NOT NULL," +
                        "creating_date INTEGER DEFAULT 0 NOT NULL," +
                        "FOREIGN KEY (step_id)  REFERENCES step_table (id) ON DELETE CASCADE)")
                database.execSQL("ALTER TABLE main_goal_table ADD COLUMN creating_date  INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE sub_goal_table ADD COLUMN creating_date INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE step_table ADD COLUMN creating_date INTEGER DEFAULT 0 NOT NULL")
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
                    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}