package com.example.herud.sensorex;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor proximity;
    private long lastUpdate = 0;
    private float[] last_values=new float[3];
    private float[] standing=new float[3];
    private float[] flat=new float[3];
    private int flag=0;
    private static final int SHAKE_THRESHOLD = 100;

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
        setContentView(R.layout.activity_main);

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

        }
        if (mySensor.getType() == Sensor.TYPE_PROXIMITY) {
            if(flag==0&&sensorEvent.values[0]==0)
            {
                flag=1;

                System.arraycopy(last_values,0,standing,0,3);
                Toast.makeText(getApplicationContext(), Integer.toString(flag)+"stand "+Float.toString(last_values[0]), Toast.LENGTH_SHORT).show();

            }
            else if(flag==1&&sensorEvent.values[0]==0)
            {
                flag=2;
                System.arraycopy(last_values,0,flat,0,3);
                Toast.makeText(getApplicationContext(), Integer.toString(flag)+"flat "+Float.toString(last_values[0]), Toast.LENGTH_SHORT).show();

            }else if(sensorEvent.values[0]==0&&flag==2){
                float temp=(standing[0]-flat[0]);
                String message= String.valueOf(last_values[0]*360/(4*10));
                //Toast.makeText(getApplicationContext(), Integer.toString(flag)+"val0 "+Float.toString(last_values[0]), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "val: "+message, Toast.LENGTH_SHORT).show();
            }


        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
