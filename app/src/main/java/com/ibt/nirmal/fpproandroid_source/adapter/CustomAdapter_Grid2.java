package com.ibt.nirmal.fpproandroid_source.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibt.nirmal.fpproandroid_source.R;
import com.ibt.nirmal.fpproandroid_source.ifls.IFLSCalculations;

import java.util.ArrayList;

import static java.lang.Math.round;

public class CustomAdapter_Grid2 extends BaseAdapter {
    Context context;
    private Activity activity;
    public static LinearLayout grid_2;
    IFLSCalculations IFLS;
    Integer logos[];
    LayoutInflater inflter;
    boolean[] iconClick = new boolean[2];
    public CustomAdapter_Grid2(Activity activity, Integer[] logos) {
        this.activity= activity;
        this.logos = logos;
    }

  /*  public CustomAdapter_Grid2(View.OnClickListener onClickListener, Integer[] iflscell) {
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
        final ArrayList<String> mItems;
        final ArrayList<Float> mScore;
        if (inflter == null)
            inflter = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflter.inflate(R.layout.grid_icon2, null);// inflate the layout
        //LinearLayout grid_layout = (LinearLayout) view.findViewById(R.id.grid_layout2);
        final TextView icon = (TextView) view.findViewById(R.id.grid2_txt1); // get the reference of ImageView
        final TextView icon2 = (TextView) view.findViewById(R.id.grid2_txt2);

        mItems = IFLS.iflsprior;
        mScore = IFLS.iflsscore;
        //icon2.setText(String.valueOf(round(mScore.get(i))));
        icon.setText(mItems.get(i));

        grid_2 = (LinearLayout) view.findViewById(R.id.grid_layout2);

        return view;
    }
}
