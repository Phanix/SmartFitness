package project.com.smartfitness;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user_workouts")
public class WorkoutDayEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exerciseOne;
    private String getExerciseTwo;
    private String day;

    public WorkoutDayEntry(String exerciseOne, String getExerciseTwo, String day) {
        this.exerciseOne = exerciseOne;
        this.getExerciseTwo = getExerciseTwo;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseOne() {
        return exerciseOne;
    }

    public void setExerciseOne(String exerciseOne) {
        this.exerciseOne = exerciseOne;
    }

    public String getGetExerciseTwo() {
        return getExerciseTwo;
    }

    public void setGetExerciseTwo(String getExerciseTwo) {
        this.getExerciseTwo = getExerciseTwo;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString(){
        return day + " : " + "Exercise One " + getExerciseOne() + " Exercise Two " + getExerciseTwo;
    }
}
