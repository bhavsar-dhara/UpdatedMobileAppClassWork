package edu.neu.madcourse.dharabhavsar1.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.firebase.client.Firebase;

import edu.neu.madcourse.dharabhavsar1.ui.dictionary.Trie;

public class MainActivity extends AppCompatActivity {
//public class MainActivity extends Application {

    Trie trie;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(getResources().getString(R.string.title_name));
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
