package com.ibt.nirmal.fpproandroid_source.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.ibt.nirmal.fpproandroid_source.R;
import com.ibt.nirmal.fpproandroid_source.adapter.CustomAdapter;
import com.ibt.nirmal.fpproandroid_source.adapter.CustomAdapter_Grid2;
import com.ibt.nirmal.fpproandroid_source.adapter.Recycler_Adapter;
import com.ibt.nirmal.fpproandroid_source.adapter.ifls_list_apapter;
import com.ibt.nirmal.fpproandroid_source.ifls.IFLSCalculations;

public class ifls_activity extends AppCompatActivity {
    private ifls_list_apapter listAdapter;
    private Recycler_Adapter mAdapter;
    private ListView listView;
    private TextView display,S,A,D,F;
    private TextView parameters;
    private GridView gridView;
    ViewHolder holder = new ViewHolder();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_list_view);



        listView = (ListView) findViewById(R.id.list_view);
        display = (TextView) findViewById(R.id.display);
        parameters = (TextView) findViewById(R.id.params);
        S = (TextView) findViewById(R.id.S);
        A = (TextView) findViewById(R.id.A);
        D = (TextView) findViewById(R.id.D);
        F = (TextView) findViewById(R.id.F);


        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ifls_activity.this, ifls_display_activity.class);
                startActivity(intent);
            }
        });

        parameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ifls_activity.this, ifls_params_settings.class);
                startActivity(intent);
            }
        });
        //gridView = (GridView) findViewById(R.id.grid_view);

        IFLSCalculations IFLSData = new IFLSCalculations(this);

        listAdapter = new ifls_list_apapter(this);
        if(holder.grid1 == null)
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                holder.grid1 = view;
                view.setSelected(true);
                view.setTag(holder);

            }
        });
           /* //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new Recycler_Adapter(this);
        recyclerView.setAdapter(mAdapter);*/
        S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*for(int i=0;i<20;i++){
                    if(listAdapter.selected_Grid1[i]){
                        CustomAdapter customAdapter = new CustomAdapter(ifls_activity.this, listAdapter.iflscell);
                        listAdapter.gridview.setAdapter(customAdapter);
                        listAdapter.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });
                    }
                    if(listAdapter.selected_Grid2[i]){
                        CustomAdapter customAdapter_Grid2 = new CustomAdapter(ifls_activity.this, listAdapter.iflscell);
                        listAdapter.gridview2.setAdapter(customAdapter_Grid2);
                        listAdapter.gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });
                    }
                }*/
            }
        });
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

    /*public class MyListActivty extends ListActivity {
        private Context context = null;
        private ListView list = null;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            list = (ListView)findViewById(android.R.id.list);

            //code to set adapter to populate list
            View footerView =  ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
            list.addFooterView(footerView);
        }
    }*/
    private class ViewHolder{
        public View grid1;
    }
}
