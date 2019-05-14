package com.ibt.nirmal.fpproandroid_source.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibt.nirmal.fpproandroid_source.R;

import java.util.Arrays;
import java.util.List;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.MyViewHolder> {

    private List<Movie> moviesList;
    private Activity activity;
    public static GridView gridview,gridview2;
    public Integer[] iflscell = {R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,
            R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,
            R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box};
    public static Boolean[] selected_Grid1 = new Boolean[100];
    public static Boolean[] selected_Grid2 = new Boolean[100];
    View row;
    public MyViewHolder holder;
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        public GridView grid1;

        public MyViewHolder(View itemView) {
            super(itemView);

            Arrays.fill(selected_Grid1, Boolean.FALSE);
            Arrays.fill(selected_Grid2,Boolean.FALSE);

            gridview = (GridView) itemView.findViewById(R.id.grid_view);
            gridview2 = (GridView) itemView.findViewById(R.id.grid_view2);
        }
    }


    public Recycler_Adapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ifls_list_layout, parent, false);

        row = itemView;
        gridview = (GridView) itemView.findViewById(R.id.grid_view);
        gridview2 = (GridView) itemView.findViewById(R.id.grid_view2);

        CustomAdapter customAdapter = new CustomAdapter(activity, iflscell);
        gridview.setAdapter(customAdapter);

        CustomAdapter_Grid2 customAdapter2 = new CustomAdapter_Grid2(activity, iflscell);
        gridview2.setAdapter(customAdapter2);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(activity, "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();
                //
                // CustomAdapter.grid_1.setBackground(view.getResources().getDrawable(R.drawable.white_border_box));
                row = view;

                if (row != null)
                {
                    if (selected_Grid1[position] == false) {
                        row.setBackgroundColor(Color.parseColor("#1589FF"));
                        row.setSelected(true);
                        holder = new MyViewHolder(row);
                        holder.grid1 = gridview;
                        selected_Grid1[position] = true;
                        //ifls_gridview = true;
                        row.setTag(holder);
                    } else {
                        row.setBackgroundColor(Color.parseColor("#00ffffff"));
                        row.setBackground(view.getResources().getDrawable(R.drawable.white_border_box));
                        row.setSelected(false);
                        holder.grid1 = gridview;
                        selected_Grid1[position] = false;
                       // ifls_gridview = true;
                        row.setTag(holder);
                    }
                }
            }
        });
        gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //CustomAdapter_Grid2.grid_2.setBackground(view.getResources().getDrawable(R.drawable.blue_border_box));
                if(selected_Grid2[position] == false) {
                    view.setBackgroundColor(Color.parseColor("#1589FF"));
                    selected_Grid2[position] = true;
                }
                else
                {
                    view.setBackgroundColor(Color.parseColor("#00ffffff"));
                    view.setBackground(view.getResources().getDrawable(R.drawable.white_border_box));
                    selected_Grid2[position] = false;
                }
            }
        });

        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.grid1 = gridview;


    }

    @Override
    public int getItemCount() {
        return iflscell.length;
    }
}
