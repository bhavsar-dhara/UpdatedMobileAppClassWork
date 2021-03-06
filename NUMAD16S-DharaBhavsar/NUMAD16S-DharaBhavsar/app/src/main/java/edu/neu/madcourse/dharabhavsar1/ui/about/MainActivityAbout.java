package edu.neu.madcourse.dharabhavsar1.ui.about;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MenuItem;

import edu.neu.madcourse.dharabhavsar1.ui.main.R;

/**
 * Created by Dhara on 1/22/2016.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MainActivityAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.about_app_screen));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
            this.getWindow().setEnterTransition(a);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
//        finish();
//        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finishAfterTransition();
//            finish();
//            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
