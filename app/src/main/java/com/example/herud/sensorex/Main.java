package com.example.herud.sensorex;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Main extends AppCompatActivity {

    private Button calibrateButton;
    private Button gameButton;
    private TextView goal;
    private Double newResult;
    private int newGoal;
    private MyArrayAdapter adapter;
    private ArrayList<ListElement>arrL;
    private Switch help;
    private boolean helpFlag=false;
    public static String key1="klucz";
    public static String key2="klucz2";


    Integer getPic()
    {
        if(Math.abs(newGoal-newResult)<10)
            return R.drawable.yespic;
        else if(Math.abs(newGoal-newResult)>20)
            return R.drawable.nopic;
        else
            return R.drawable.maybepic;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                newResult=data.getDoubleExtra("result",0);
                arrL.add(new ListElement(newGoal,newResult,getPic()));
                adapter.notifyDataSetChanged();

            }else{
                Toast.makeText(getApplicationContext(),"no value received",Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        calibrateButton=(findViewById(R.id.calibrate));
        gameButton=(findViewById(R.id.game));
        goal=findViewById(R.id.goal);

        newGoal=(int)(Math.random()*180);
        goal.setText(Double.toString(newGoal));
        help=findViewById(R.id.help);



        calibrateButton.setOnClickListener(randomButton());

        gameButton.setOnClickListener(startButton());
        help.setOnCheckedChangeListener(helpSwitch());




        arrL=new ArrayList<>();
        ListView lv = (ListView)findViewById(R.id.myListView);
        adapter=new MyArrayAdapter(this, arrL);
        lv.setAdapter(adapter);




    }

    View.OnClickListener randomButton()
    {
        View.OnClickListener listener=new View.OnClickListener()
        {
            public void onClick(View view)
            {
                newGoal=(int)(Math.random()*180);
                goal.setText(Double.toString(newGoal));


            }
        };

        return listener;
    }

    View.OnClickListener startButton()
    {
        View.OnClickListener listener=new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent i = new Intent(Main.this, TheGame.class);
                i.putExtra(key2,helpFlag);
                i.putExtra(key1, newGoal);
                startActivityForResult(i, 1);



            }
        };


        return listener;
    }

    CompoundButton.OnCheckedChangeListener helpSwitch()
    {
        CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    helpFlag=true;
                }
                else {
                    helpFlag=true;
                }
            }
        };


        return listener;
    }


}
