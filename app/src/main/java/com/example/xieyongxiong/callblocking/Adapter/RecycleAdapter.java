package com.example.xieyongxiong.callblocking.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.xieyongxiong.callblocking.R;

import java.util.List;

/**
 * Created by xieyongxiong on 16-6-9.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.myViewHolder>{

    private Context mcontext;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private List<String> data;

    public RecycleAdapter(Context mcontext, List<String> data) {
        this.mcontext = mcontext;
        this.data = data;
        inflater = LayoutInflater.from(mcontext);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_recycle,parent,false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        holder.textView.setText("Tel -  "+data.get(position));

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
        return data.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public myViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.item_text);
        }
    }

    public String AdpterData(int position){
        return data.get(position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
