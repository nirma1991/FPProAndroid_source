package com.ibt.nirmal.fpproandroid_source.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibt.nirmal.fpproandroid_source.R;
import com.ibt.nirmal.fpproandroid_source.ifls.IFLSCalculations;

import java.util.ArrayList;

import static java.lang.Math.round;

public class CustomAdapter extends BaseAdapter {
    Context context;
    private Activity activity;
    public static LinearLayout grid_1;
    IFLSCalculations IFLS;
    Integer logos[];
    LayoutInflater inflater;
    TextView icon,icon2;
    boolean[] iconClick = new boolean[2];
    public CustomAdapter(Activity activity, Integer[] logos) {
        this.activity= activity;
        this.logos = logos;
    }

    /*public CustomAdapter(View.OnClickListener onClickListener, Integer[] iflscell)
    {
        this.activity= (Activity) onClickListener;
    }*/

    @Override
    public int getCount() {
        return logos.length;
    }
    @Override
    public Object getItem(int i) {
        return 1;
    }
    @Override
    public long getItemId(int i) {
        return 1;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //View row = view;
        //ViewHolder holder;
        final ArrayList<String> mItems;
        final ArrayList<Float> mScore;
        if (view == null) {
            if (inflater == null)
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(R.layout.grid_icon, null);

            icon = (TextView) view.findViewById(R.id.icon);
            icon2 = (TextView) view.findViewById(R.id.icon2);

        }

       // view = inflater.inflate(R.layout.grid_icon, null);// inflate the layout
        //LinearLayout grid_layout = (LinearLayout) view.findViewById(R.id.grid_layout2);
       // final TextView icon = (TextView) view.findViewById(R.id.icon); // get the reference of ImageView
       // final TextView icon2 = (TextView) view.findViewById(R.id.icon2);

        mItems = IFLS.iflsprior;
        mScore = IFLS.iflsscore;
        icon.setText(mItems.get(i));
        //icon2.setText(String.valueOf(round(mScore.get(i))));
        //grid_1 = (LinearLayout) row.findViewById(R.id.grid_layout);


        return view;
    }

    static class ViewHolder {
        TextView icon,icon2;
        LinearLayout grid_1;
    }
}
