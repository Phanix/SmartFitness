package project.com.smartfitness;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class UserProgress extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>  {
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress);

        SharedPreferences prefs = getSharedPreferences(Helper.USER_PREFS_KEY, MODE_PRIVATE);

        //Setup days of workout
        int numberOfWorkoutDays = prefs.getInt(Helper.USER_PREF_NUMBER_OF_WORKOUT_DAYS_KEY, 0);
        TextView tvNumberWorkoutsDay = findViewById(R.id.tv_days_of_workout);
        tvNumberWorkoutsDay.setText(getResources().getString(R.string.days_of_workout_label) + " : " + numberOfWorkoutDays);
        //Setup initial weight
        float initialWeight       = prefs.getFloat(Helper.USER_PREF_INITIAL_WEIGHT_KEY, 0);
        TextView tvInitialWeight = findViewById(R.id.tv_initial_weight);
        tvInitialWeight.setText(getResources().getString(R.string.initial_weight_label) + " : " + initialWeight);
        //Setup actual  weight
        float actualWeight        = prefs.getFloat(Helper.USER_PREF_ACTUAL_WEIGHT_KEY, 0);
        TextView tvActualWeight = findViewById(R.id.tv_actual_weight);
        tvActualWeight.setText(getResources().getString(R.string.actual_weight_label) + " : " + actualWeight);



        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        mDialog = new AlertDialog.Builder(UserProgress.this).create();
        mDialog.setIcon(R.drawable.weight);
        final FloatingActionButton quoteRequestButton = findViewById(R.id.request_quote_button);
        quoteRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              requestQuote();
            }
        });




    }

    public void requestQuote(){
        getSupportLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks)this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        //return new FetchData object
        return new FetchData(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        Quote quote = Helper.jsonParser(s);
        if(quote == null){
            quote = new Quote(getResources().getString(R.string.no_internet_connection), getResources().getString(R.string.no_internet_info));
        }
        if(!mDialog.isShowing()) {
            mDialog.setTitle(quote.getAuthor());
            mDialog.setMessage(quote.getText());


            mDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            mDialog.show();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public static class FetchData extends AsyncTaskLoader<String>{


        public FetchData(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public String loadInBackground() {
            String data = "";
            try{
                URL url = new URL("https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en");
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                data = Helper.convertInputStream(inputStream);
                httpURLConnection.disconnect();


            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

            return data;
        }

        @Override
        public void deliverResult(@Nullable String data) {
            super.deliverResult(data);
        }
    }


}
