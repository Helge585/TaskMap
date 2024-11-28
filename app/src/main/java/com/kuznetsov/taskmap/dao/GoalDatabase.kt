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
import com.kuznetsov.taskmap.entity.ThisDayTask
import com.kuznetsov.taskmap.entity.ThisDayTask_UnusedClass_MustBeDeleted

@Database(entities = [MainGoal::class, SubGoal::class, Step::class, Increment::class,
    ThisDayTask::class, ThisDayTask_UnusedClass_MustBeDeleted::class],
    version = 7, exportSchema = false)
abstract class GoalDatabase : RoomDatabase() {

    abstract val mainGoalDao: MainGoalDao
    abstract val subGoalDao: SubGoalDao
    abstract val stepDao: StepDao
    abstract val thisDayTaskDao: ThisDayTaskDao

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

        private val MIGRATION_3_4 = object: Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS this_day_task_table (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "this_day_task_name TEXT DEFAULT '-' NOT NULL," +
                        "description TEXT DEFAULT '-' NOT NULL," +
                        "step_id INTEGER NOT NULL," +
                        "status INTEGER DEFAULT 0 NOT NULL," +
                        "creating_date INTEGER DEFAULT 0 NOT NULL," +
                        "FOREIGN KEY (step_id)  REFERENCES step_table (id) ON DELETE CASCADE)")
            }
        }

        private val MIGRATION_4_5 = object: Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS this_day_task_table_2 (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "this_day_task_name TEXT DEFAULT '-' NOT NULL," +
                        "description TEXT DEFAULT '-' NOT NULL," +
                        "step_id INTEGER NOT NULL," +
                        "status INTEGER DEFAULT 0 NOT NULL," +
                        "creating_date INTEGER DEFAULT 0 NOT NULL)")
            }
        }

        private val MIGRATION_5_6 = object: Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE this_day_task_table_2 ADD COLUMN start_result INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE this_day_task_table_2 ADD COLUMN current_result INTEGER DEFAULT 0 NOT NULL")
                database.execSQL("ALTER TABLE this_day_task_table_2 ADD COLUMN finish_result INTEGER DEFAULT 0 NOT NULL")
            }
        }

        private val MIGRATION_6_7 = object: Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE this_day_task_table_2 ADD COLUMN group_id INTEGER DEFAULT -1 NOT NULL")
                database.execSQL("CREATE TABLE IF NOT EXISTS this_day_group_table (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "this_day_group_name TEXT DEFAULT '-' NOT NULL)")
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
                    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4,
                        MIGRATION_4_5, MIGRATION_5_6, MIGRATION_6_7).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}