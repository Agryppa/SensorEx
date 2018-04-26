package com.example.herud.sensorex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Herud on 2018-04-25.
 */

public class MyArrayAdapter extends ArrayAdapter {
    private ArrayList list;

    public MyArrayAdapter(Context context, ArrayList<ListElement> list) {

        super(context, 0, list);
        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ListElement element = (ListElement) getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element, parent, false);
        }






        TextView nameT = (TextView) convertView.findViewById(R.id.expected);
        TextView lastNameT = (TextView) convertView.findViewById(R.id.result);
        ImageView picture =(ImageView)convertView.findViewById(R.id.pic) ;



        nameT.setText(Double.toString(Math.round(element.getExpectedX())));
        lastNameT.setText(Double.toString(Math.round(element.getResultX())));
        picture.setImageResource(element.getPic());



        return convertView;
    }




}
