package project.com.smartfitness;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    AlertDialog mInitialWeightDialog;
    AlertDialog mActualWeightDialog;

    RadioGroup mRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mRadioGroup = findViewById(R.id.radio_goal_options);

        SharedPreferences sharedPreferences = getSharedPreferences(Helper.USER_PREFS_KEY, MODE_PRIVATE);
        String userGoal = sharedPreferences.getString(Helper.USER_PREF_GOAL_KEY, Helper.USER_DEFAULT_GOAL);
        if(userGoal.equals(Helper.USER_GOAL_GAIN_MUSCLE)){
            mRadioGroup.check(R.id.rb_gain_muscle);
        }else if(userGoal.equals(Helper.USER_GOAL_LOSE_WEIGHT)){
            mRadioGroup.check(R.id.rb_lose_weight);
        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                SharedPreferences.Editor editor = getSharedPreferences(Helper.USER_PREFS_KEY, MODE_PRIVATE).edit();
                if(checkedId == R.id.rb_gain_muscle){
                    editor.putString(Helper.USER_PREF_GOAL_KEY, Helper.USER_GOAL_GAIN_MUSCLE);
                }else if(checkedId == R.id.rb_lose_weight){
                    editor.putString(Helper.USER_PREF_GOAL_KEY, Helper.USER_GOAL_LOSE_WEIGHT);
                }

                editor.apply();
            }
        });

        //Setup initial weight
        final TextView tvInitialWeight = findViewById(R.id.tv_initial_weight_settings);
        updateWeightTextView(getResources().getString(R.string.initial_weight_label) + " : ",tvInitialWeight, Helper.USER_PREF_INITIAL_WEIGHT_KEY);

        //Setup actual  weight
        final TextView tvActualWeight = findViewById(R.id.tv_actual_weight_settings);
        updateWeightTextView(getResources().getString(R.string.actual_weight_label) + " : ", tvActualWeight, Helper.USER_PREF_ACTUAL_WEIGHT_KEY);


        final EditText editTextInitialWeight = new EditText(getBaseContext());
        final EditText editTextActualWeight = new EditText(getBaseContext());
        editTextActualWeight.setTextColor(Color.BLACK);
        editTextInitialWeight.setTextColor(Color.BLACK);
        mInitialWeightDialog = new AlertDialog.Builder(Settings.this).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newWeightString = editTextInitialWeight.getText().toString();

                try{
                    float newWeightFloat = Float.parseFloat(newWeightString);
                    SharedPreferences.Editor editor = getSharedPreferences(Helper.USER_PREFS_KEY, MODE_PRIVATE).edit();
                    editor.putFloat(Helper.USER_PREF_INITIAL_WEIGHT_KEY, newWeightFloat);
                    editor.apply();
                    updateWeightTextView(getResources().getString(R.string.initial_weight_label) + " : ", tvInitialWeight, Helper.USER_PREF_INITIAL_WEIGHT_KEY);
                }catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_number_exception), Toast.LENGTH_SHORT).show();
                }

            }
        }).create();
        mInitialWeightDialog.setMessage(getResources().getString(R.string.dialog_settings_question));
        mInitialWeightDialog.setView(editTextInitialWeight);

        mActualWeightDialog = new AlertDialog.Builder(Settings.this).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newWeightString = editTextActualWeight.getText().toString();

                try{
                    float newWeightFloat = Float.parseFloat(newWeightString);
                    SharedPreferences.Editor editor = getSharedPreferences(Helper.USER_PREFS_KEY, MODE_PRIVATE).edit();
                    editor.putFloat(Helper.USER_PREF_ACTUAL_WEIGHT_KEY, newWeightFloat);
                    editor.apply();
                    updateWeightTextView(getResources().getString(R.string.actual_weight_label) + " : ", tvActualWeight, Helper.USER_PREF_ACTUAL_WEIGHT_KEY);
                }catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_number_exception), Toast.LENGTH_SHORT).show();
                }
            }
        }).create();
        mActualWeightDialog.setMessage(getResources().getString(R.string.dialog_settings_question));
        mActualWeightDialog.setView(editTextActualWeight);

        tvActualWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mActualWeightDialog.isShowing()){
                    mActualWeightDialog.show();
                }
            }
        });

        tvInitialWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mInitialWeightDialog.isShowing()){
                    mInitialWeightDialog.show();
                }
            }
        });

    }

    public void updateWeightTextView(String text, TextView weightTextView, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(Helper.USER_PREFS_KEY, MODE_PRIVATE);
        float value = sharedPreferences.getFloat(key, 0);
        weightTextView.setText(text + value);

    }

}

