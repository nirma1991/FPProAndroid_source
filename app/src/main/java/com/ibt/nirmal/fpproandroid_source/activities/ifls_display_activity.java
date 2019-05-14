package com.ibt.nirmal.fpproandroid_source.activities;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ibt.nirmal.fpproandroid_source.DateTimeWheel.DateWheel.LoopListener;
import com.ibt.nirmal.fpproandroid_source.DateTimeWheel.DateWheel.LoopView;
import com.ibt.nirmal.fpproandroid_source.R;
import com.ibt.nirmal.fpproandroid_source.database.DatabaseHelper;
import com.ibt.nirmal.fpproandroid_source.database.model.params;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ifls_display_activity extends AppCompatActivity implements View.OnClickListener {
    private static final int DEFAULT_MIN_YEAR = 1900;

    public LoopView yearLoopView;
    public LoopView monthLoopView;
    public LoopView dayLoopView;
    public Button confirmBtn;

    private int yearPos = 0;
    private int monthPos = 0;
    private int dayPos = 0;

    ArrayList yearList = new ArrayList();
    ArrayList monthList = new ArrayList();
    ArrayList dayList = new ArrayList();

    private TextView[] colorValue = new TextView[3];
    private LinearLayout[] colorLayout = new LinearLayout[3];
    private String[] ids,colorRange;
    private double factor;
    private int periodvalue;

    private int minYear;
    private int maxYear;
    private int viewTextSize;
    private DatabaseHelper db;
    private TextView lastDate,period;
    Thread selectDate;
    Calendar today,periodDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ifls_display_params);

        minYear = DEFAULT_MIN_YEAR;
        viewTextSize = 25;
        maxYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
        lastDate = (TextView)  findViewById(R.id.periodDate);
        period = (TextView)  findViewById(R.id.period);

        int temp,templayout;
        ids  = new String[]{"yellow","orange","red"};
        colorRange = new String[]{"yellow_range","orange_range","red_range"};

        for(int i=0; i<colorValue.length; i++){
            temp = getResources().getIdentifier(ids[i], "id", getPackageName());
            colorValue[i] = (TextView)findViewById(temp);
        }
        for(int i=0; i<colorLayout.length; i++){
            templayout = getResources().getIdentifier(colorRange[i], "id", getPackageName());
            colorLayout[i] = (LinearLayout)findViewById(templayout);
        }
        today = Calendar.getInstance();
        periodDate = Calendar.getInstance();

        /*selectDate = new Thread(){
            @Override
            public void run() {
                while (!selectDate.isInterrupted()){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {



                            }
                        });
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // restore interrupted status
                    }
                }
            }
        };selectDate.start();*/

        setSelectedDate();

        initialiseDateWheel();



      /*  // prepare note object
        params note = new params(4.0,5,16,2,12,12,
                             1,2,1.5,1,6,12,
                            14,10,0,20,6,true,
                            true,4,false,true,
                            true,0.8,3,4);*/

    }
    private void initialiseDateWheel() {

        yearLoopView = (LoopView) findViewById(R.id.picker_year);
        monthLoopView = (LoopView) findViewById(R.id.picker_month);
        dayLoopView = (LoopView) findViewById(R.id.picker_day);

        //do not loop,default can loop
        yearLoopView.setNotLoop();
        monthLoopView.setNotLoop();
        dayLoopView.setNotLoop();

        //set loopview text btnTextsize
        yearLoopView.setTextSize(viewTextSize);
        monthLoopView.setTextSize(viewTextSize);
        dayLoopView.setTextSize(viewTextSize);

        //set checked listen
        yearLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                /*if (isLeapYear(item)) {
                    isLeapYear = true;
                }else{
                    isLeapYear = false;
                }*/
                yearPos = item;
                initDayPickerView();
            }
        });
        monthLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                monthPos = item;
                initDayPickerView();
            }
        });
        dayLoopView.setListener(


                new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                dayPos = item;
               // initDayPickerView();
                setSelectedDateWheel();
            }
        });

        initPickerViews(); // init year and month loop view
        initDayPickerView(); //init day loop view
    }

    public void setSelectedDate() {

        yearPos = today.get(Calendar.YEAR) - minYear;
        monthPos = today.get(Calendar.MONTH);
        dayPos = today.get(Calendar.DAY_OF_MONTH) - 1;
        periodDate.add(today.DATE, Integer.parseInt(period.getText().toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd,MMMM yyyy");
        lastDate.setText(sdf.format(periodDate.getTime()));

    }

    public void setSelectedDateWheel(){

        periodDate = getCalendar(dayPos,monthPos,yearPos);
        periodDate.add(Calendar.DATE, Integer.parseInt(period.getText().toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd,MMMM yyyy");
        lastDate.setText(sdf.format(periodDate.getTime()));
    }


    public Calendar getCalendar(int day, int month, int year)
    {
        Calendar date = Calendar.getInstance();
        date.get(Calendar.YEAR);

        // We will have to increment the month field by 1

        date.set(Calendar.MONTH, month);

        // As the month indexing starts with 0

        date.set(Calendar.DAY_OF_MONTH, day);

        return date;
    }
    private void initPickerViews() {

        int yearCount = maxYear - minYear;

        for (int i = 0; i < yearCount; i++) {
            yearList.add(format2LenStr(minYear + i));
        }

        for (int j = 0; j < 12; j++) {
            monthList.add(format2LenStr(j + 1));
        }

        yearLoopView.setArrayList((ArrayList) yearList);
        yearLoopView.setInitPosition(yearPos);

        monthLoopView.setArrayList((ArrayList) monthList);
        monthLoopView.setInitPosition(monthPos);
    }

    /**
     * Init day item
     */
    private void initDayPickerView() {

        int dayMaxInMonth;
        Calendar calendar = Calendar.getInstance();
        dayList = new ArrayList<String>();

        calendar.set(Calendar.YEAR, minYear + yearPos);
        calendar.set(Calendar.MONTH, monthPos);

        //get max day in month
        dayMaxInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < dayMaxInMonth; i++) {
            dayList.add(format2LenStr(i + 1));
        }

        dayLoopView.setArrayList((ArrayList) dayList);
        dayLoopView.setInitPosition(dayPos);

      /*  yearPos = calendar.get(Calendar.YEAR) - minYear;
        monthPos = calendar.get(Calendar.MONTH);
        dayPos = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        calendar.add(Calendar.DATE, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd,MMMM yyyy");
        lastDate.setText(sdf.format(calendar.getTime()));*/

    }

    public static String format2LenStr(int num) {

        return (num < 10) ? "0" + num : String.valueOf(num);
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
        else {

        }
        switch (v.getId()) {

            case R.id.increase:
                for(int i=0;i<colorValue.length;i++)
                {
                    ViewGroup id = ((ViewGroup) v.getParent());
                    ViewGroup id2= ((ViewGroup) colorLayout[i]);
                    if(id.equals(id2))
                    {
                        factor = Double.parseDouble(colorValue[i].getText().toString());
                        factor += 0.1;
                        colorValue[i].setText(String.format("%.1f", factor));
                    }
                }

                break;
            case R.id.decrease:
                for(int i=0;i<colorValue.length;i++)
                {
                    {
                        ViewGroup id = ((ViewGroup) v.getParent());
                        ViewGroup id2= ((ViewGroup) colorLayout[i]);
                        if(id.equals(id2)) {
                            factor = Double.parseDouble(colorValue[i].getText().toString());
                            if(factor != 0.0)
                            {
                                factor -= 0.1;
                                colorValue[i].setText(String.format("%.1f", factor));
                            }
                        }
                    }
                }
                break;
            case R.id.increasePeriod:
                    periodvalue = Integer.parseInt(period.getText().toString());
                    periodvalue += 1;
                    period.setText(String.format("%d", periodvalue));

                break;
            case R.id.decreasePeriod:
                        periodvalue = Integer.parseInt(period.getText().toString());
                        if(periodvalue != 0)
                        {
                            periodvalue -= 1;
                            period.setText(String.format("%d",periodvalue));
                        }

                break;
        }

    }
}
