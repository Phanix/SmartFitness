package project.com.smartfitness;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutDays extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_days);

        ButterKnife.bind(this);

    }

    @OnClick({R.id.monday_button, R.id.tuesday_button, R.id.wednesday_button, R.id.thursday_button, R.id.friday_button})
    public void clickListener(View view){
        String content  = "";
        switch (view.getId()){
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
