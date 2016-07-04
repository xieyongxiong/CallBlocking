package com.example.xieyongxiong.callblocking;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.xieyongxiong.callblocking.Fragment.BlackListFragment;
import com.example.xieyongxiong.callblocking.Fragment.LocationFragment;
import com.example.xieyongxiong.callblocking.Fragment.MainFragment;
import com.example.xieyongxiong.callblocking.Utils.Fragment_select;



public class MainActivity extends AppCompatActivity implements MainFragment.onFragmentSelectedListener,
        View.OnTouchListener{

    MainFragment mainFragment;

    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFragment=new MainFragment();
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,mainFragment).commit();

    }

    @Override
    public void onSelected(int flag) {
        Log.e("THG","fragment get");
        switch (flag){
            case Fragment_select.blacklist:
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new BlackListFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case Fragment_select.time_block:
                startActivity(new Intent(getApplicationContext(),TimeActivity.class));
                break;

            case Fragment_select.contact_block:
                startActivity(new Intent(getApplicationContext(),ContactActivity.class));
                break;

            case Fragment_select.location_block:
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new LocationFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;

        }



    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public interface mTouchListener{
        void mTouch();
    }
}
