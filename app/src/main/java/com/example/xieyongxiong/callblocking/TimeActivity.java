package com.example.xieyongxiong.callblocking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends AppCompatActivity implements View.OnTouchListener,ClockView.view{

    private ClockView clockView;
    private TextView time_text,save_text;
    private Button button_save;
    SharedPreferences preferences;
    int startHour,endHour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        time_text = (TextView) findViewById(R.id.time_text);
        save_text = (TextView) findViewById(R.id.text_now);
        button_save = (Button) findViewById(R.id.time_save);
        clockView = (ClockView) findViewById(R.id.clockView);

        preferences =getSharedPreferences("Block",MODE_PRIVATE);
        startHour = preferences.getInt("startHour",0);
        endHour = preferences.getInt("endHour",0);
        save_text.setText(startHour+" - "+endHour);
        assert button_save != null;

        button_save.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("startHour",startHour);
            editor.putInt("endHour",endHour);
            editor.commit();
            save_text.setText(startHour+" - "+endHour);
            Snackbar.make(button_save,"保存成功",Snackbar.LENGTH_SHORT).show();

        });

        clockView.setOnTouchListener(this);
        clockView.setClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return clockView.onTouch(v,event);
    }


    @Override
    public void showText(int x, int y) {
        time_text.setText(x+" - "+y);
        startHour = x;
        endHour = y;
    }

}
