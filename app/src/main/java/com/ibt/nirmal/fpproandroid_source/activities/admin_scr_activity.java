package com.ibt.nirmal.fpproandroid_source.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ibt.nirmal.fpproandroid_source.DateTimeWheel.DateWheel.LoopListener;
import com.ibt.nirmal.fpproandroid_source.DateTimeWheel.DateWheel.LoopView;
import com.ibt.nirmal.fpproandroid_source.R;

import java.util.ArrayList;
import java.util.Calendar;

public class admin_scr_activity extends AppCompatActivity {
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

    private int minYear;
    private int maxYear;
    private int viewTextSize;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_scr);

        minYear = DEFAULT_MIN_YEAR;
        viewTextSize = 25;
        maxYear = Calendar.getInstance().get(Calendar.YEAR) + 1;

        setSelectedDate();

        initialiseDateWheel();
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
        dayLoopView.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                dayPos = item;
            }
        });

        initPickerViews(); // init year and month loop view
        initDayPickerView(); //init day loop view
    }

    public void setSelectedDate() {

        Calendar today = Calendar.getInstance();
        yearPos = today.get(Calendar.YEAR) - minYear;
        monthPos = today.get(Calendar.MONTH);
        dayPos = today.get(Calendar.DAY_OF_MONTH) - 1;

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
    }

    public static String format2LenStr(int num) {

        return (num < 10) ? "0" + num : String.valueOf(num);
    }
}
