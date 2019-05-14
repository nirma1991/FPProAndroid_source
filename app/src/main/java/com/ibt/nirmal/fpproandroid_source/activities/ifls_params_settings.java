package com.ibt.nirmal.fpproandroid_source.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ibt.nirmal.fpproandroid_source.R;
import com.ibt.nirmal.fpproandroid_source.database.DatabaseHelper;
import com.ibt.nirmal.fpproandroid_source.database.model.params;
import com.ibt.nirmal.fpproandroid_source.ifls.IFLSCalculations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ifls_params_settings extends AppCompatActivity implements View.OnClickListener {
    private DatabaseHelper db;
    private params parameter;
    private TextView[] params = new TextView[20];
    private TextView[] paramstxtids = new TextView[20];
    private TextView resetTxt,fatacttxt,awakefactortxt,maxdutydtxt,maxdutyntxt,ntfromtxt,ntuntiltxt;
    private String[] paramsid,paramstxt;
    private double factor;
    private boolean increase,decrease;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.params_layout);

        int temp,tmptxt;
        paramsid  = new String[]{"r1factor","r2factor","r1sc0hr","r1maxsc","r2sc0hr","r2maxsc",
                                "r3factor","r3reset","fatactfactor","maxdutyd","maxdutyn","ntfrom",
                                "ntuntil"};

       /* paramstxt  = new String[]{"r1factortxt","r2factortxt","r3factortxt","resetTxt","fatacttxt",
                                    "maxdutydtxt","maxdutyntxt","ntfromtxt","ntuntiltxt"};*/

        for(int i=0; i<paramsid.length; i++){
            temp = getResources().getIdentifier(paramsid[i], "id", getPackageName());
            params[i] = (TextView)findViewById(temp);
        }
       /* for(int i=0; i<paramstxt.length; i++){
            tmptxt = getResources().getIdentifier(paramsid[i], "id", getPackageName());
            paramstxtids[i] = (TextView)findViewById(tmptxt);
        }*/
        resetTxt = (TextView) findViewById(R.id.resettxt);
        fatacttxt = (TextView) findViewById(R.id.fatacttxt);
        awakefactortxt = (TextView) findViewById(R.id.awakefactortxt);
        maxdutydtxt = (TextView) findViewById(R.id.maxdutydtxt);
        maxdutyntxt = (TextView) findViewById(R.id.maxdutyntxt);
        ntfromtxt = (TextView) findViewById(R.id.ntfromtxt);
        ntuntiltxt = (TextView) findViewById(R.id.ntuntiltxt);

        db = new DatabaseHelper(this);

        db.insertParams(4.0,5,16,2,12,12,
                1,2,1.5,1,6,12,
                14,10,0,20,6,true,
                true,4,false,true,
                true,0.8,3,4);

        try {
            params n = db.getNote();
            JSONArray array = db.resultSet;
            IFLSCalculations IFLSData = new IFLSCalculations(this);
            //IFLSData.calculateIFLS(0, 0,false);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getTag().equals("increase")) {
            v.setId(R.id.increase);
        }
        else  if(v.getTag().equals("decrease"))
        {
            v.setId(R.id.decrease);
        }
        switch (v.getId()) {

            case R.id.increase:
               // for(int i=0;i<params.length;i++)
                {
                    ViewGroup id = ((ViewGroup) v.getParent());
                    //ViewGroup id2= ((ViewGroup) params[i].getParent());
                    if(id.equals((ViewGroup) params[0].getParent()))
                    {
                        factor = Double.parseDouble(params[0].getText().toString());
                        if(factor < 5.0) {
                            factor += 0.1;
                            params[0].setText(String.format("%.1f", factor));
                        }
                    }
                    else if(id.equals((ViewGroup) params[1].getParent()))
                    {
                        factor = Double.parseDouble(params[1].getText().toString());
                        if(factor < 2.5) {
                            factor += 0.1;
                            params[1].setText(String.format("%.1f", factor));
                        }
                    }
                    else if (id.equals((ViewGroup) params[6].getParent()))
                    {
                        factor = Double.parseDouble(params[6].getText().toString());
                        if(factor < 1.2) {
                            factor += 0.1;
                            params[6].setText(String.format("%.1f", factor));
                            awakefactortxt.setText(String.format("Awake > Prior factor : %.1f" , factor));
                        }
                    }
                    else if (id.equals((ViewGroup) params[7].getParent()))
                    {
                        factor = Double.parseDouble(params[7].getText().toString());
                        if(factor < 6.0) {
                            factor += 1;
                            params[7].setText(String.format("%.1f", factor));
                            resetTxt.setText(String.format("After %.1f hours of sleep" , factor));
                        }
                    }
                    else  if (id.equals((ViewGroup) params[8].getParent()))
                    {
                        factor = Double.parseDouble(params[8].getText().toString());
                        if(factor < 3.0) {
                            factor += 0.1;
                            params[8].setText(String.format("%.1f", factor));
                            fatacttxt.setText(String.format("= %.1f hours awake" , factor));
                        }
                    }
                    else if (id.equals((ViewGroup) params[9].getParent()))
                    {
                        factor = Double.parseDouble(params[9].getText().toString());
                        if(factor < 24.0) {
                            factor += 1;
                            params[9].setText(String.format("%.0f", factor));
                            maxdutydtxt.setText(String.format("%.0f hrs" , factor));
                        }
                    }
                    else if (id.equals((ViewGroup) params[10].getParent()))
                    {
                        factor = Double.parseDouble(params[10].getText().toString());
                        if(factor < 24.0) {
                            factor += 1;
                            params[10].setText(String.format("%.0f", factor));
                            maxdutyntxt.setText(String.format("%.0f hrs" , factor));
                        }
                    }
                    else if (id.equals((ViewGroup) params[11].getParent()))
                    {
                        factor = Double.parseDouble(params[11].getText().toString());
                        if(factor < 24.0) {
                            factor += 1;
                            params[11].setText(String.format("%.0f", factor));
                            ntfromtxt.setText(String.format(" %.0f " , factor));
                        }
                    }
                    else if (id.equals((ViewGroup) params[12].getParent()))
                    {
                        factor = Double.parseDouble(params[12].getText().toString());
                        if(factor < 9.0) {
                            factor += 1;
                            params[12].setText(String.format("%.0f", factor));
                            ntuntiltxt.setText(String.format(" %.0f 0' clock" , factor));
                        }
                    }
                }

                break;
            case R.id.decrease:
                //for(int i=0;i<params.length;i++)
                {
                        ViewGroup id = ((ViewGroup) v.getParent());
                        //ViewGroup id2= ((ViewGroup) params[i].getParent());
                        if(id.equals((ViewGroup) params[0].getParent()))
                        {
                            factor = Double.parseDouble(params[0].getText().toString());
                            if(factor > 3.0) {
                                factor -= 0.1;
                                params[0].setText(String.format("%.1f", factor));
                            }
                        }
                        else if(id.equals((ViewGroup) params[1].getParent()))
                        {
                            factor = Double.parseDouble(params[1].getText().toString());
                            if(factor > 1.5) {
                                factor -= 0.1;
                                params[1].setText(String.format("%.1f", factor));

                            }
                        }
                    else if (id.equals((ViewGroup) params[6].getParent()))
                    {
                        factor = Double.parseDouble(params[6].getText().toString());
                        if(factor  > 0.8) {
                            factor -= 0.1;
                            params[6].setText(String.format("%.1f", factor));
                            awakefactortxt.setText(String.format("Awake > Prior factor : %.1f" , factor));
                        }
                    }
                        else if (id.equals((ViewGroup) params[7].getParent()))
                    {
                        factor = Double.parseDouble(params[7].getText().toString());
                        if(factor > 1.0) {
                            factor -= 1;
                            params[7].setText(String.format("%.1f", factor));
                            resetTxt.setText(String.format("After %.1f hours of sleep" , factor));
                        }
                    }
                        else  if (id.equals((ViewGroup) params[8].getParent()))
                    {
                        factor = Double.parseDouble(params[8].getText().toString());
                        if(factor > 1.0) {
                            factor -= 0.1;
                            params[8].setText(String.format("%.1f", factor));
                            fatacttxt.setText(String.format("= %.1f hours awake" , factor));
                        }
                    }
                    else if (id.equals((ViewGroup) params[9].getParent()))
                    {
                        factor = Double.parseDouble(params[9].getText().toString());
                        if(factor > 4.0) {
                            factor -= 1;
                            params[9].setText(String.format("%.0f", factor));
                            maxdutydtxt.setText(String.format("%.0f hrs" , factor));
                        }
                    }
                        else if (id.equals((ViewGroup) params[10].getParent()))
                    {
                        factor = Double.parseDouble(params[10].getText().toString());
                        if(factor > 4.0) {
                            factor -= 1;
                            params[10].setText(String.format("%.0f", factor));
                            maxdutyntxt.setText(String.format("%.0f hrs" , factor));
                        }
                    }
                    else if (id.equals((ViewGroup) params[11].getParent()))
                    {
                        factor = Double.parseDouble(params[11].getText().toString());
                        if(factor > 17.0) {
                            factor -= 1;
                            params[11].setText(String.format("%.0f", factor));
                            ntfromtxt.setText(String.format(" %.0f " , factor));
                        }
                    }
                        else if (id.equals((ViewGroup) params[12].getParent()))
                    {
                        factor = Double.parseDouble(params[12].getText().toString());
                        if(factor > 4.0) {
                            factor -= 1;
                            params[12].setText(String.format("%.0f", factor));
                            ntuntiltxt.setText(String.format(" %.0f 0' clock " , factor));
                        }
                    }
                }
        }
    }
}
