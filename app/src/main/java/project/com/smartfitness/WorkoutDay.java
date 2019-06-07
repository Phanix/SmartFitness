package project.com.smartfitness;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WorkoutDay extends AppCompatActivity {
    String day;
    public final static String EXTRA_DAY = "extra";
    int resourseId = 0;
    int numberOfDays = 0;
    String userGoal;
    Boolean numberOfDaysSet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_day);

        SharedPreferences sharedPreferences = getSharedPreferences(Helper.USER_PREFS_KEY, MODE_PRIVATE);
         userGoal = sharedPreferences.getString(Helper.USER_PREF_GOAL_KEY, Helper.USER_DEFAULT_GOAL);
         numberOfDays = sharedPreferences.getInt(Helper.USER_PREF_NUMBER_OF_WORKOUT_DAYS_KEY, 0);
        Button button = findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOfDaysSet == false) {
                    numberOfDaysSet = true;
                    SharedPreferences.Editor editor = getSharedPreferences(Helper.USER_PREFS_KEY, MODE_PRIVATE).edit();
                    numberOfDays++;
                    editor.putInt(Helper.USER_PREF_NUMBER_OF_WORKOUT_DAYS_KEY, numberOfDays);
                    editor.apply();

                }
                DatabaseTask databaseTask = new DatabaseTask();
                if (userGoal.equals(Helper.USER_GOAL_LOSE_WEIGHT)) {
                    databaseTask.execute(new WorkoutDayEntry(Helper.loseWeightNames[resourseId], Helper.loseWeightNames[resourseId + 1], day));
                }else{
                    databaseTask.execute(new WorkoutDayEntry(Helper.gainWeightNames[resourseId], Helper.gainWeightNames[resourseId + 1], day));
                }
            }
        });

        TextView tvOneInstruction = findViewById(R.id.tv_one_instruction);
        TextView tvTwoInstruction = findViewById(R.id.tv_two_instruction);

        ImageView ivExerciseOne = findViewById(R.id.iv_exercise_one);
        ImageView ivExerciseTwo = findViewById(R.id.iv_exercise_two);



         day = getIntent().getStringExtra(EXTRA_DAY);

        resourseId = Helper.dayToResourceId(day);

        if (userGoal.equals(Helper.USER_GOAL_LOSE_WEIGHT)) {
            ivExerciseOne.setImageResource(Helper.loseWeightImages[resourseId]);
            ivExerciseTwo.setImageResource(Helper.loseWeightImages[resourseId + 1]);
            tvOneInstruction.setText(Helper.loseWeightInstruction[resourseId]);
            tvTwoInstruction.setText(Helper.loseWeightInstruction[resourseId + 1]);

        }
       else{
            ivExerciseOne.setImageResource(Helper.gainWeightImages[resourseId]);
            ivExerciseTwo.setImageResource(Helper.gainWeightImages[resourseId + 1]);
            tvOneInstruction.setText(Helper.gainWeightInstruction[resourseId]);
            tvTwoInstruction.setText(Helper.gainWeightInstruction[resourseId + 1]);
        }
    }

    private class DatabaseTask extends AsyncTask<WorkoutDayEntry, Void, Void>{


        @Override
        protected Void doInBackground(WorkoutDayEntry... workoutDayEntries) {
            WorkoutDayDatabase workoutDayDatabase = Room.databaseBuilder(getApplicationContext(), WorkoutDayDatabase.class, WorkoutDayDatabase.DATABASE_NAME).build();
            workoutDayDatabase.mWorkoutDayDAO().insert(workoutDayEntries[0]);
            workoutDayDatabase.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }
    }
}
