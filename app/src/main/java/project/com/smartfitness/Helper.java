package project.com.smartfitness;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import project.com.smartfitness.R;

public class Helper {
    public static final String USER_PREFS_KEY = "UserPreferences";
    public static final String USER_PREF_GOAL_KEY = "userGoal";
    public static final String USER_PREF_INITIAL_WEIGHT_KEY = "initialWeight";
    public static final String USER_PREF_NUMBER_OF_WORKOUT_DAYS_KEY = "numberOfWorkoutsDay";
    public static final String USER_PREF_ACTUAL_WEIGHT_KEY = "actualWeight";
    public static final String USER_GOAL_GAIN_MUSCLE = "gainMuscle";
    public static final String USER_GOAL_LOSE_WEIGHT = "loseWeight";
    public static final String USER_DEFAULT_GOAL = USER_GOAL_LOSE_WEIGHT;
    public static String convertInputStream(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while(line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }

       return stringBuilder.toString();
    }


    public static Quote jsonParser(String data){
        Quote quote = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String author = jsonObject.getString("quoteAuthor");
            String text   = jsonObject.getString("quoteText");
            quote = new Quote(author, text);
        }catch (JSONException e){
            e.printStackTrace();
        }

        return quote;
    }
    public static int dayToResourceId(String day) {
        int resourseId = 0;
        day = day.toUpperCase();

        switch (day) {
            case "TUESDAY":
                resourseId = 2;
                break;
            case "WEDNESDAY":
                resourseId = 4;
                break;
            case "THURSDAY":
                resourseId = 6;
                break;
            case "FRIDAY":
                resourseId = 8;
                break;

        }
        return resourseId;
    }

    public static int [] gainWeightImages = {
      R.drawable.abs,
      R.drawable.chest,
      R.drawable.punch,
      R.drawable.running,
      R.drawable.chest,
      R.drawable.abs,
      R.drawable.arm,
      R.drawable.leg,
      R.drawable.abs,
      R.drawable.chest
    };

    public static String [] gainWeightNames = {
            "abs",
            "chest",
            "punch",
            "running",
            "chest",
            "abs",
            "arm",
            "leg",
            "abs",
            "chest"
    };

    public static int [] gainWeightInstruction = {
            R.string.abs_instruction,
            R.string.chest_instruction,
            R.string.punch_instruction,
            R.string.running_instruction,
            R.string.chest_instruction,
            R.string.abs_instruction,
            R.string.arm_instruction,
            R.string.leg_instruction,
            R.string.abs_instruction,
            R.string.chest_instruction
    };

    public static int [] loseWeightImages = {
            R.drawable.running,
            R.drawable.jump,
            R.drawable.exerciseicycle,
            R.drawable.exerciseicycle,
            R.drawable.running,
            R.drawable.jump,
            R.drawable.treadmill,
            R.drawable.treadmill,
            R.drawable.running,
            R.drawable.running
    };
    public static String [] loseWeightNames = {
            "running",
            "jump",
            "Bicycle",
            "Bicycle",
            "running",
            "jump",
            "treadmill",
            "treadmill",
            "running",
            "running"
    };

    public static int [] loseWeightInstruction = {
            R.string.running_instruction,
            R.string.jump_instruction,
            R.string.bicycle_instruction,
            R.string.bicycle_instruction,
            R.string.running_instruction,
            R.string.jump_instruction,
            R.string.running_instruction,
            R.string.running_instruction,
            R.string.running_instruction,
            R.string.running_instruction
    };



}
