package project.com.smartfitness;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


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

}
