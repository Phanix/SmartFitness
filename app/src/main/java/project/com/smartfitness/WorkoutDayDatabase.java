package project.com.smartfitness;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = WorkoutDayEntry.class, version = 1, exportSchema = false)
public abstract class WorkoutDayDatabase extends RoomDatabase {

    public static String DATABASE_NAME = "user_db";

    public abstract WorkoutDayDAO mWorkoutDayDAO();
}
