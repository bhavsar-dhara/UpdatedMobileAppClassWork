package edu.neu.madcourse.dharabhavsar1.ui.main;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;


public class SensorActivity extends Activity {

    private static final String TAG = "SensorActivity";

    private Sensor gyro;
    private Sensor linearAccelero;

    private SensorManager mSensorManager;
    private SensorEventListener mSensorListener;
    private TextView displayData;
    private boolean bite = false;

    private int gyroCount = 0;

    private float[] gyroCheck = new float[3];

    private DeviceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trickiest);

        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                displayData = (TextView)findViewById(R.id.counter);
            }
        });

        mSensorManager = ((SensorManager) getSystemService(Context.SENSOR_SERVICE));
        gyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        linearAccelero = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        client = DeviceClient.getInstance(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopMeasurement();
    }

    @Override
    public void onResume(){
        super.onResume();
        startMeasurement();
    }

    protected void startMeasurement() {
        mSensorListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor arg0, int arg1) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {

                if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
                    if(event.values[0] == gyroCheck[0] &&
                            event.values[1] == gyroCheck[1] &&
                            event.values[2] == gyroCheck[2]){
                        gyroCount++;
                        if(gyroCount > 20 && mSensorManager != null) {
                            mSensorManager.unregisterListener(mSensorListener, gyro);
                            Log.e(TAG, "Unregistering Gyroscope");
                        }
                        return;
                    }
                    else{
                        gyroCount = 0;
                        gyroCheck[0] = event.values[0];
                        gyroCheck[1] = event.values[1];
                        gyroCheck[2] = event.values[2];
                    }
                }
                boolean check = client.sendSensorData(event.sensor.getType(), event.accuracy, event.timestamp, event.values);
                if(bite != check){
                    updateBiteDisplay(check);
                    bite = check;
                }
            }
        };

        mSensorManager.registerListener(mSensorListener,
                gyro, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorListener,
                linearAccelero, SensorManager.SENSOR_DELAY_UI);
    }

    private void stopMeasurement() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(mSensorListener);
            mSensorListener = null;
        }
    }

    private void updateBiteDisplay(boolean bite){
        if(bite){
            displayData.setText(R.string.bite_detected);
            displayData.setBackgroundColor(getResources().getColor(R.color.green));
        }
        else{
            displayData.setText(R.string.take_bite);
            displayData.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
}
