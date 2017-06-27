package com.developer.ti.mapa.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

/**
 * Created by tecnicoairmovil on 27/06/17.
 */

import com.developer.ti.mapa.Model.Item;
import com.developer.ti.mapa.R;

import java.util.List;

public class AdapterItem extends RecyclerView.Adapter {

    private List<Item> list;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public AdapterItem(Context mContext, List<Item> list, RecyclerView mRecyclerView) {
        this.list = list;
        this.mContext = mContext;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            final Item mItem = list.get(position);
            final MyViewHolder myViewHolder = (MyViewHolder) holder;

            myViewHolder.title.setText("-->" + mItem.getTitle());
            myViewHolder.subtitle.setText("** " + mItem.getSubtitle());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        }
    }

}

