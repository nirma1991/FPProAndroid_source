package com.ibt.nirmal.fpproandroid_source.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.ibt.nirmal.fpproandroid_source.R;

import java.util.Arrays;

public class ifls_list_apapter extends BaseAdapter {
    private Context context;
    private Activity activity;
    private ListAdapter adapter;
    private LayoutInflater inflater;
    public static Boolean[] selected_Grid1 = new Boolean[100];
    public static Boolean[] selected_Grid2 = new Boolean[100];
    public static GridView gridview,gridview2;
    View row;
    View row2 = null;
    public ViewHolder holder = new ViewHolder();
    private Boolean ifls_gridview = false;



    public ifls_list_apapter(Activity activity) {
        this.activity = activity;
        this.context=context;
        this.adapter =adapter;



        Arrays.fill(selected_Grid1, Boolean.FALSE);
        Arrays.fill(selected_Grid2,Boolean.FALSE);
    }

    public Integer[] iflscell = {R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,
                                R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,
                                R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box,R.drawable.white_border_box};

    @Override
    public int getCount() {
        return iflscell.length;
    }

    @Override
    public Object getItem(int position) {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout grid_main = null;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.ifls_list_layout,  parent, false);

        }
        else {
            if (row  != null) {
                holder = new ViewHolder();
                holder = (ViewHolder) row.getTag();
                notifyDataSetChanged();
                // convertView = row;
            }
        }

        gridview = (GridView) convertView.findViewById(R.id.grid_view);
        gridview2 = (GridView) convertView.findViewById(R.id.grid_view2);
        grid_main = (LinearLayout) convertView.findViewById(R.id.grid_layout_main);

       // if(ifls_gridview == false)
        {
            CustomAdapter customAdapter = new CustomAdapter(activity, iflscell);
            gridview.setAdapter(customAdapter);

            CustomAdapter_Grid2 customAdapter2 = new CustomAdapter_Grid2(activity, iflscell);
            gridview2.setAdapter(customAdapter2);

        }
       /* if(convertView != null) {
            holder = new ViewHolder();
            holder = (ViewHolder) row.getTag();
        }*/
        View finalConvertView = convertView;
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                   // isScrollStop = true;
                    notifyDataSetChanged();
                } else {
                    //isScrollStop = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
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
                            holder = new ViewHolder();
                            holder.grid1 = gridview;

                            selected_Grid1[position] = true;
                            ifls_gridview = true;
                            row.setTag(holder);
                        } else {
                            row.setBackgroundColor(Color.parseColor("#00ffffff"));
                            row.setBackground(view.getResources().getDrawable(R.drawable.white_border_box));
                            row.setSelected(false);
                            holder.grid1 = gridview;
                            selected_Grid1[position] = false;
                            ifls_gridview = true;
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

        if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Do some stuff
            grid_main.setOrientation(LinearLayout.HORIZONTAL);
        }
      /*  else
        {
            grid_main.setOrientation(LinearLayout.VERTICAL);
        }*/
        //convertView.setTag(holder);

        return convertView;
    }

    private class ViewHolder{
        public GridView grid1;
    }
}
