package project.com.smartfitness;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WorkoutDayDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WorkoutDayEntry workoutDayEntry);

    @Query("SELECT * FROM user_workouts")
    List<WorkoutDayEntry> getAll();

    @Query("SELECT * FROM user_workouts WHERE day = :dayId")
    List<WorkoutDayEntry> getAllByDay(String dayId);

}
