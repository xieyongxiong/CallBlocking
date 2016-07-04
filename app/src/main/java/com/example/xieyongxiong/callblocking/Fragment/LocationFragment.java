package com.example.xieyongxiong.callblocking.Fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xieyongxiong.callblocking.Adapter.location_adapter;
import com.example.xieyongxiong.callblocking.DB.location_dao;
import com.example.xieyongxiong.callblocking.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    private List<String> data ;
    private RecyclerView recyclerView;
    private location_adapter adapter;
    private location_dao dao;

    public LocationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        dao = new location_dao(getContext());
        loadData();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new location_adapter(getContext(),data);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new location_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String loc=adapter.AdpterData(position);
                if(data.contains(loc)){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());//先得到构造器
                    builder.setTitle("提示"); //设置标题
                    builder.setMessage("是否将 "+loc+" 从归属地拦截中移除?"); //设置内容
                    builder.setIcon(R.drawable.black_dialog);//设置图标，图片id即可

                    builder.setPositiveButton("确定", (dialog, which) -> {
                        dao.delete(loc);
                        dialog.dismiss(); //关闭dialog
                        Snackbar.make(getView(),"删除成功",Snackbar.LENGTH_SHORT).show();

                    });
                    builder.setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                    });

                    builder.create().show();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());//先得到构造器
                    builder.setTitle("提示"); //设置标题
                    builder.setMessage("是否将 "+loc+" 添加到归属地拦截中?"); //设置内容
                    builder.setIcon(R.drawable.black_dialog);//设置图标，图片id即可

                    builder.setPositiveButton("确定", (dialog, which) -> {
                        dao.add(loc);
                        dialog.dismiss(); //关闭dialog
                        Snackbar.make(getView(),"添加成功",Snackbar.LENGTH_SHORT).show();

                    });
                    builder.setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                    });

                    builder.create().show();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        return view;
    }

    public void loadData(){
        data = dao.query();
    }

}
