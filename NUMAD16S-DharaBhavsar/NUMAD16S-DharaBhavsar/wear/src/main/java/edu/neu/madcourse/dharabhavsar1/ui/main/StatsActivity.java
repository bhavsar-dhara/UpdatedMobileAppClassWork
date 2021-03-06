package edu.neu.madcourse.dharabhavsar1.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.activity.ConfirmationActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Anirudh on 4/28/2016.
 */
public class StatsActivity extends Activity {

    private static final String TAG = "StatsActivity";

    private TextView mealDuration;
    private TextView yourBiteInterval;
    private TextView suggBiteInterval;
    private View horizontalLine;
    private View verticalLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "in StatsActivity");

        super.onCreate(savedInstanceState);

        // Show Confirmation
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                getString(R.string.all_set));
        startActivity(intent);

        setContentView(R.layout.activity_stats);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        notificationManager.cancel(1);

        long timeDuration = System.currentTimeMillis();
        int bites = 1;
        int biteInterval = 0;

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_SHARED, MODE_PRIVATE);
        timeDuration = timeDuration - sharedPreferences.getLong(Constants.MEAL_START_TIME, timeDuration);
        bites = sharedPreferences.getInt(Constants.MEAL_BITES, bites);
        biteInterval = sharedPreferences.getInt(Constants.SUGGESTED_BITE_INTERVAL, biteInterval);

        int yourBInterval = 0;
        if(bites != 0)
            yourBInterval = (int) timeDuration/(bites*1000);


        horizontalLine = findViewById(R.id.stats_horizontal_line);
        verticalLine = findViewById(R.id.stats_vertical_line);

        mealDuration = (TextView)findViewById(R.id.stats_meal_duration);
        yourBiteInterval = (TextView)findViewById(R.id.stats_your_bite_interval);
        suggBiteInterval = (TextView)findViewById(R.id.stats_suggested_bite_interval);

        //Format the time
        int minutes = (int)timeDuration/60000;
        Integer seconds = (int)timeDuration/1000 - (60 * minutes);
        String displayTime = seconds.toString().length() == 2 ?
                String.valueOf(minutes) + ":" + seconds.toString() :
                String.valueOf(minutes) + ":" + "0"+seconds.toString();

        mealDuration.setText(displayTime);

        String notApplicable = "NA";

        if(bites != 0) {
            yourBiteInterval.setText(String.valueOf(yourBInterval));
        }
        else {
            yourBiteInterval.setText(notApplicable);
        }

        if(biteInterval != 0){
            suggBiteInterval.setText(String.valueOf(biteInterval));
        }
        else{
            suggBiteInterval.setText(notApplicable);
        }

        if(yourBInterval < biteInterval){
            horizontalLine.setBackgroundColor(Color.RED);
            verticalLine.setBackgroundColor(Color.RED);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();

        //Clear the shared Preferences
        SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_SHARED, MODE_PRIVATE)
                .edit();

        editor.remove(Constants.MEAL_START_TIME);
        editor.remove(Constants.MEAL_BITES);
        editor.remove(Constants.SUGGESTED_BITE_INTERVAL);
        editor.apply();
    }

}