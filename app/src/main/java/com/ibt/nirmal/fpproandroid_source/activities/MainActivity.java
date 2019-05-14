package com.ibt.nirmal.fpproandroid_source.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ibt.nirmal.fpproandroid_source.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView Ifls_scr=(TextView)findViewById(R.id.Start_fatigue_scr);
        TextView Update_scr=(TextView)findViewById(R.id.update_info);
        TextView Buy_subscription = (TextView)findViewById(R.id.buy_subs);
        TextView Admin_menu = (TextView)findViewById(R.id.admin_menu);
        TextView Info = (TextView) findViewById(R.id.info);

        Update_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Update_scr_activity.class);
                startActivity(intent);
            }
        });

        Buy_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, subscription_activity.class);
                startActivity(intent);
            }
        });

        Admin_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, admin_scr_activity.class);
                startActivity(intent);
            }
        });

        Ifls_scr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ifls_activity.class);
                startActivity(intent);
            }
        });

        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, info_activity.class);
                startActivity(intent);
            }
        });
    }
}
