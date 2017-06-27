package com.developer.ti.mapa.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.ti.mapa.Model.Test;
import com.google.android.gms.vision.text.Text;

import java.util.List;

import com.developer.ti.mapa.R;

/**
 * Created by tecnicoairmovil on 27/06/17.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder>{

    private List<Test> testList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewSection;
        public ImageView ImageViewIcon;

        public MyViewHolder(View view) {
            super(view);
            textViewSection = (TextView) view.findViewById(R.id.text_view_section);
            ImageViewIcon = (ImageView) view.findViewById(R.id.image_view_icon);
        }
    }

    public TestAdapter(List<Test> testList) {
        this.testList = testList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_test, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Test mTest = testList.get(position);
        holder.textViewSection.setText(mTest.getSection());
        holder.ImageViewIcon.setImageResource(mTest.getIcon());
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

}
