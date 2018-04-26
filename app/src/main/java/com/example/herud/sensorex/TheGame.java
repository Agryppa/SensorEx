package com.example.herud.sensorex;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class TheGame extends AppCompatActivity implements SensorEventListener{
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor proximity;
    private TextView text;
    private TextView goalText;
    private boolean helpFlagX=false;
    private int goal;
    private float[] last_values=new float[3];
    private final double g=9.81;
    private final double twoPi=6.28;


    @Override
    protected void onPause()
    {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        text=findViewById(R.id.textView);
        goalText=findViewById(R.id.goal);

        goal=getIntent().getIntExtra(Main.key1,0);
        goalText.setText(Integer.toString(goal));
        helpFlagX=getIntent().getBooleanExtra(Main.key2,false);

        senSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        proximity=senSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        senSensorManager.registerListener(this, proximity , SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];


            last_values[0] = x;
            last_values[1] = y;
            last_values[2] = z;

            if(helpFlagX)
                text.setText(Double.toString(Math.acos(last_values[0]/g)*360/twoPi));
                //text.setText(Double.toString(last_values[1]));
            else
                text.setText("");
        }
        if (mySensor.getType() == Sensor.TYPE_PROXIMITY) {
            if(sensorEvent.values[0]==0)
            {
                double res = Math.acos(last_values[0]/g)*360/twoPi;
                //Toast.makeText(getApplicationContext(), "val: " + Double.toString(res), Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", res);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
