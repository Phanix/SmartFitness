package project.com.smartfitness;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreatePanelMenu(featureId, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_config){
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseTaskRetrieve databaseTaskRetrieve = new DatabaseTaskRetrieve();
                databaseTaskRetrieve.execute();
            }
        });



        MobileAds.initialize(this, "ca-app-pub-9421395053871893~6589228071");

        ImageView weight_button_image   = findViewById(R.id.weight_button);
        ImageView progress_button_image = findViewById(R.id.progress_button);
        weight_button_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WorkoutDays.class);
                startActivity(intent);
            }
        });

        progress_button_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserProgress.class);
                startActivity(intent);
            }
        });

    }



    private class DatabaseTaskRetrieve extends AsyncTask<Void, Void, List<WorkoutDayEntry>>{

        @Override
        protected List<WorkoutDayEntry> doInBackground(Void... voids) {
            WorkoutDayDatabase workoutDayDatabase = Room.databaseBuilder(getApplicationContext(), WorkoutDayDatabase.class, WorkoutDayDatabase.DATABASE_NAME).build();
            return workoutDayDatabase.mWorkoutDayDAO().getAll();
        }

        @Override
        protected void onPostExecute(List<WorkoutDayEntry> workoutDayEntries) {
            super.onPostExecute(workoutDayEntries);
            StringBuilder datatoShare = new StringBuilder();
            for(WorkoutDayEntry workoutDayEntry : workoutDayEntries){
                datatoShare.append(workoutDayEntry.toString());
                datatoShare.append("\n");
            }

            startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(MainActivity.this)
                    .setType("text/plain")
                    .setText(datatoShare.toString())
                    .getIntent(), getString(R.string.action_share)));
        }
    }
}
