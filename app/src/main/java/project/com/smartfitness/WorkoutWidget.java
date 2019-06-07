package project.com.smartfitness;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.ToIntBiFunction;

/**
 * Implementation of App Widget functionality.
 */
public class WorkoutWidget extends AppWidgetProvider {


    static String getDay(){
        java.text.SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        return   dayFormat.format(calendar.getTime());
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

       String weekDay = getDay();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.workout_widget);

        int dayResource = Helper.dayToResourceId(weekDay);
        if(!weekDay.equals("Sunday") && !weekDay.equals("Saturday")) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Helper.USER_PREFS_KEY, Context.MODE_PRIVATE);
            if(sharedPreferences.getString(Helper.USER_PREF_GOAL_KEY, Helper.USER_DEFAULT_GOAL).equals(Helper.USER_GOAL_LOSE_WEIGHT)){
                views.setImageViewResource(R.id.widget_image_one, Helper.loseWeightImages[dayResource]);
                views.setImageViewResource(R.id.widget_image_two, Helper.loseWeightImages[dayResource + 1]);
            }else{
                views.setImageViewResource(R.id.widget_image_one, Helper.gainWeightImages[dayResource]);
                views.setImageViewResource(R.id.widget_image_two, Helper.gainWeightImages[dayResource + 1]);
            }

        }else{
            views.setImageViewResource(R.id.widget_image_one, R.drawable.relax);
            views.setImageViewResource(R.id.widget_image_two, R.drawable.weekend);
        }
        // Construct the RemoteViews object

        views.setTextViewText(R.id.appwidget_text, weekDay);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            String day = getDay();
            Intent intent;
            if(day.equals("Sunday") || day.equals("Saturday")) {

                 intent = new Intent(context, MainActivity.class);
            }else{
                intent = new Intent(context, WorkoutDay.class);
                intent.putExtra(WorkoutDay.EXTRA_DAY, day.toUpperCase());
            }
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.workout_widget);


            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            remoteViews.setOnClickPendingIntent(R.id.widget, configPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

