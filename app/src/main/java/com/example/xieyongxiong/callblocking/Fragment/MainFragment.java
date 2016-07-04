package com.example.xieyongxiong.callblocking.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.xieyongxiong.callblocking.R;
import com.example.xieyongxiong.callblocking.Utils.Fragment_select;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private onFragmentSelectedListener mCallback;
    private ImageButton ib_blacklist,ib_time_block, ib_contact_block,ib_loc_block,ib_exit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ib_blacklist=(ImageButton)view.findViewById(R.id.blacklist_block);
        ib_time_block=(ImageButton)view.findViewById(R.id.time_block);
        ib_contact_block =(ImageButton)view.findViewById(R.id.contact_block);
        ib_loc_block = (ImageButton)view.findViewById(R.id.location_block);
        ib_exit = (ImageButton)view.findViewById(R.id.ib_exit);

        ib_blacklist.setOnClickListener(this);
        ib_time_block.setOnClickListener(this);
        ib_contact_block.setOnClickListener(this);
        ib_loc_block.setOnClickListener(this);
        ib_exit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback=(onFragmentSelectedListener) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.blacklist_block:
                mCallback.onSelected(Fragment_select.blacklist);
                break;
            case R.id.time_block:
                mCallback.onSelected(Fragment_select.time_block);
                break;
            case R.id.contact_block:
                mCallback.onSelected(Fragment_select.contact_block);
                break;
            case R.id.location_block:
                mCallback.onSelected(Fragment_select.location_block);
                break;
            case R.id.ib_exit:
                android.os.Process.killProcess(android.os.Process.myPid());
                break;

        }
    }


    public interface onFragmentSelectedListener{
         void onSelected(int flag);
    }




}
