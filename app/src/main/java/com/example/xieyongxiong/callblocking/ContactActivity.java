package com.example.xieyongxiong.callblocking;

import android.content.SharedPreferences;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences preferences;
    private boolean contact_open;
    private FrameLayout container;
    private OpenFragment f1;
    private CloseFragment f2;
    public class OpenFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_open,container,false);
        }
    }

    public class CloseFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_close,container,false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        container = (FrameLayout) findViewById(R.id.container);
        preferences = getSharedPreferences("Block",MODE_PRIVATE);
        contact_open = preferences.getBoolean("contact_open",false);
        Log.e("thg",contact_open+"out");
        container.setOnClickListener(this);
        f1 = new OpenFragment();
        f2 = new CloseFragment();

        if(contact_open){

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,f1)
                    .commit();
        }else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,f2)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        if(contact_open){
            contact_open = false;
            new Thread(r).start();
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.container,f2)
                    .commit();

        }else {
            contact_open = true;
            new Thread(r).start();

            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.container,f1)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    Runnable r = () -> {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("contact_open",contact_open);
        editor.commit();
        Log.e("thg",contact_open+"in");
    };
}
