package com.example.xieyongxiong.callblocking.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xieyongxiong.callblocking.Adapter.RecycleAdapter;
import com.example.xieyongxiong.callblocking.Adapter.location_adapter;
import com.example.xieyongxiong.callblocking.DB.blacklist_dao;
import com.example.xieyongxiong.callblocking.R;

import java.util.List;

import static android.support.v7.app.AlertDialog.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlackListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView toolbar_title;
    private FloatingActionButton floatingActionButton,add_cancel;
    private blacklist_dao dao;
    private RecycleAdapter adapter;
    private List<String> data;
    private EditText et_add;
    private Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_black_list, container, false);

        toolbar_title = (TextView)view.findViewById(R.id.toolbar_title);

        recyclerView =(RecyclerView)view.findViewById(R.id.recycleView);

        et_add = (EditText)view.findViewById(R.id.et_add);

        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.blacklist_add);

        add_cancel = (FloatingActionButton)view.findViewById(R.id.add_cancel);


        dao = new blacklist_dao(getActivity());

        data = dao.query();

        toolbar_title.setText(R.string.toolbar_title_blacklist);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(manager);

        manager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new RecycleAdapter(getActivity(),data);

        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
                String number = adapter.AdpterData(position);
                delete(number,position);
            }
        });



        floatingActionButton.setOnClickListener(View-> {

            if (et_add.getVisibility() == View.GONE) {

                et_add.setVisibility(android.view.View.VISIBLE);

                add_cancel.setVisibility(android.view.View.VISIBLE);

            }else if(et_add.getVisibility()== android.view.View.VISIBLE){

                if(!et_add.getText().toString().equals("")){
                    String num=et_add.getText().toString();

                    dao.insert(num);

                    data.add(adapter.getItemCount(),num);
                    adapter.notifyItemInserted(adapter.getItemCount());

                    Snackbar.make(getView(),"保存成功",Snackbar.LENGTH_LONG).show();

                    et_add.setText("");

                    et_add.setVisibility(android.view.View.GONE);

                    add_cancel.setVisibility(android.view.View.GONE);

                }else{

                }

            }
        });

        add_cancel.setOnClickListener(View->{

            et_add.setVisibility(android.view.View.GONE);

            add_cancel.setVisibility(android.view.View.GONE);

        });
        return view;
    }

    private void delete(String number,int position){

        Builder builder=new Builder(getActivity());//先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否将 "+number+" 从黑名单中移除?"); //设置内容
        builder.setIcon(R.drawable.black_dialog);//设置图标，图片id即可

        builder.setPositiveButton("确定", (dialog, which) -> {
            dao.delete(number);
            data.remove(position);
            adapter.notifyItemRemoved(position);
            dialog.dismiss(); //关闭dialog
            Snackbar.make(getView(),"删除成功",Snackbar.LENGTH_SHORT).show();

        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }

    @Override
    public void onDestroy() {
        dao.close();
        super.onDestroy();
    }
}
