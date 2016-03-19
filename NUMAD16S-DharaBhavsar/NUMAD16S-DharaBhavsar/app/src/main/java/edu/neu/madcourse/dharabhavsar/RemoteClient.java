package edu.neu.madcourse.dharabhavsar;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import edu.neu.madcourse.dharabhavsar.model.communication.GameData;
import edu.neu.madcourse.dharabhavsar.model.communication.UserData;

/**
 * Created by derylrodrigues on 3/4/16.
 */
public class RemoteClient {

    private static final String MyPREFERENCES = "MyPrefs" ;
//    private static final String FIREBASE_DB = "https://<Your FireBase AppName>.firebaseIO.com/";
    private static final String FIREBASE_DB = "https://popping-fire-5271.firebaseio.com/";
    private static final String TAG = "RemoteClient";
    private static boolean isDataChanged = false;
    private Context mContext;
    private HashMap<String, String> fireBaseData = new HashMap<String, String>();


    public RemoteClient(Context mContext)
    {
        this.mContext = mContext;
        Firebase.setAndroidContext(mContext);
//        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);
    }


    public void saveValue(String key, String value) {
        Firebase ref = new Firebase(FIREBASE_DB);
        Firebase usersRef = ref.child(key);
        usersRef.setValue(value, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.d(TAG, "Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.d(TAG, "Data saved successfully.");
                }
            }
        });
    }

    public boolean isDataFetched()
    {
        return isDataChanged;
    }

    public String getValue(String key)
    {
        return fireBaseData.get(key);
    }

    public void fetchValue(String key) {

        Log.d(TAG, "Get Value for Key - " + key);
        Firebase ref = new Firebase(FIREBASE_DB + key);
        Query queryRef = ref.orderByKey();
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // snapshot contains the key and value
                isDataChanged = true;
                if(snapshot.getValue() != null)
                {
                    Log.d(TAG, "Data Received" + snapshot.getValue().toString());
                    // Adding the data to the HashMap
                    fireBaseData.put(snapshot.getKey(), snapshot.getValue().toString());
                }
                else {
                    Log.d(TAG, "Data Not Received");
                    fireBaseData.put(snapshot.getKey(), null);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, firebaseError.getMessage());
                Log.e(TAG, firebaseError.getDetails());
            }
        });
    }

//    code to save and retrieve userData
    public void saveUserData(UserData value) {
        Firebase ref = new Firebase(FIREBASE_DB);
        Firebase usersRef = ref.child("userData");
        usersRef.push().setValue(value, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.d(TAG, "Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.d(TAG, "Data saved successfully.");
                }
            }
        });
    }

    public void fetchUserData(String key, final String userId) {
        Log.d(TAG, "Get Value for Key - " + key);
        Firebase ref = new Firebase(FIREBASE_DB + key);
        Query queryRef = ref.orderByKey();
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // snapshot contains the key and value
                if (snapshot.getValue() != null) {
                    Log.d(TAG, "There are " + snapshot.getChildrenCount() + " blog posts");
                    // Adding the data to the HashMap
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (postSnapshot.getKey().equals(userId)) {
                            UserData user = postSnapshot.getValue(UserData.class);
                            Log.e(TAG, user.getUserId() + " - " + user.getUserName()
                                    + " - " + user.getUserCombineBestScore() + " - " + user.getUserIndividualBestScore());
                        }
                    }
                } else {
                    Log.d(TAG, "Data Not Received");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, firebaseError.getMessage());
                Log.e(TAG, firebaseError.getDetails());
            }
        });
    }

//    code to store gameData
    public void saveGameData(GameData value) {
        Firebase ref = new Firebase(FIREBASE_DB);
        Firebase gameRef = ref.child("gameData");
        gameRef.push().setValue(value, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.d(TAG, "Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.d(TAG, "Data saved successfully.");
                }
            }
        });
    }

    public void fetchGameData(String key, final String userId) {
        Log.d(TAG, "Get Value for Key - " + key);
        Firebase ref = new Firebase(FIREBASE_DB + key);
        Query queryRef = ref.orderByKey();
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // snapshot contains the key and value
                if (snapshot.getValue() != null) {
                    Log.d(TAG, "There are " + snapshot.getChildrenCount() + " blog posts");
                    // Adding the data to the HashMap
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        if (postSnapshot.getKey().equals(userId)) {
                            UserData user = postSnapshot.getValue(UserData.class);
                            Log.e(TAG, user.getUserId() + " - " + user.getUserName()
                                    + " - " + user.getUserCombineBestScore() + " - " + user.getUserIndividualBestScore());
                        }
                    }
                } else {
                    Log.d(TAG, "Data Not Received");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG, firebaseError.getMessage());
                Log.e(TAG, firebaseError.getDetails());
            }
        });
    }
}