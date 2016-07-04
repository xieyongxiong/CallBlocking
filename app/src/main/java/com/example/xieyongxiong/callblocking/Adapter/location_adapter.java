package com.example.xieyongxiong.callblocking.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.xieyongxiong.callblocking.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyongxiong on 16-6-9.
 */
public class location_adapter extends RecyclerView.Adapter<location_adapter.myViewHolder>{

    private Context mcontext;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private List<String> data;
    private List<String> province;

    public location_adapter(Context mcontext, List<String> data) {
        this.mcontext = mcontext;
        this.data = data;
        inflater = LayoutInflater.from(mcontext);
        province = new ArrayList<>();
        String s="北京市，天津市，重庆市，上海市，河北省，山西省，辽宁省，吉林省，黑龙江省，江苏省，浙江省，安徽省，" +
                "福建省，江西省，山东省，河南省，湖北省，湖南省，广东，海南省，四川省，贵州省，云南省，陕西省，甘肃省，" +
                "青海省，台湾省，内蒙古自治区，广西壮族自治区，" +
                "西藏自治区，宁夏回族自治区，新疆维吾尔自治区，香港特别行政区，澳门特别行政区";
        String []str=s.split("，");
        for (String pro:str) {
            province.add(pro);
        }
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_location,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        int r=(int)(200+Math.random()*200);
        holder.textView.setHeight(r);
        holder.textView.setText(province.get(position));
        if(data.contains(province.get(position))){
            holder.textView.setBackgroundColor(Color.GRAY);
        }else {
            holder.textView.setBackgroundColor(Color.parseColor("#cc9999"));
        }

        if(onItemClickListener!=null){
            holder.textView.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                onItemClickListener.onItemClick(holder.textView,pos);
            });
            holder.textView.setOnLongClickListener(v -> {
                int pos = holder.getLayoutPosition();
                onItemClickListener.onItemLongClick(holder.textView,pos);
                return false;
            });
        }

    }

    @Override
    public int getItemCount() {
        return province.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public myViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.item_text);
        }
    }

    public String AdpterData(int position){
        return province.get(position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
