package project.com.smartfitness;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class WorkoutDays extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_days);
        TextView tv_monday    = findViewById(R.id.monday_button);
        TextView tv_tuesday   = findViewById(R.id.tuesday_button);
        TextView tv_wednesday = findViewById(R.id.wednesday_button);
        TextView tv_thursday  = findViewById(R.id.thursday_button);
        TextView tv_friday    = findViewById(R.id.friday_button);

        tv_monday.setOnClickListener(this);
        tv_tuesday.setOnClickListener(this);
        tv_wednesday.setOnClickListener(this);
        tv_thursday.setOnClickListener(this);
        tv_friday.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String content  = "";
        switch (id){
            case R.id.monday_button:
                content = getResources().getString(R.string.monday);
                break;
            case R.id.tuesday_button:
                content = getResources().getString(R.string.tuesday);
                break;
            case R.id.wednesday_button:
                content = getResources().getString(R.string.wednesday);
                break;
            case R.id.thursday_button:
                content = getResources().getString(R.string.thursday);
                break;
            case R.id.friday_button:
                content = getResources().getString(R.string.friday);
                break;

        }

        Intent intent = new Intent(WorkoutDays.this, WorkoutDay.class);
        intent.putExtra(WorkoutDay.EXTRA_DAY, content);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
