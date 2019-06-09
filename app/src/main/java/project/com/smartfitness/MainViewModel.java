package project.com.smartfitness;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.List;



public class MainViewModel extends AndroidViewModel {

    private LiveData<List<WorkoutDayEntry>> mListLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mListLiveData = MainActivity.workoutDayDatabase.mWorkoutDayDAO().getAll();
    }

    public LiveData<List<WorkoutDayEntry>> getListLiveData() {
        return mListLiveData;
    }


    public class DatabaseTaskRetrieve extends AsyncTask<Void, Void, LiveData<List<WorkoutDayEntry>>> {

        @Override
        protected LiveData<List<WorkoutDayEntry>> doInBackground(Void... voids) {
            Log.i("ViewModel", "execute");
            return MainActivity.workoutDayDatabase.mWorkoutDayDAO().getAll();
        }

        @Override
        protected void onPostExecute(LiveData<List<WorkoutDayEntry>> workoutDayEntries) {
            super.onPostExecute(workoutDayEntries);
            mListLiveData = workoutDayEntries;

        }


    }

}
