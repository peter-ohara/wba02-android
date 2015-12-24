package com.pascoapp.wba02_android.setup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.School;

import java.util.List;

/**
 * Created by Kwaku on 12/23/2015.
 */
public class ChooseSchoolAdapter extends RecyclerView.Adapter {

    private List<School> mSchools;
    private OnItemClickListener mItemClickListener;

    public ChooseSchoolAdapter(List<School> mSchool) {
        this.mSchools = mSchool;
    }

    public class SchoolViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public School school;

        public SchoolViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.school_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, school);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.school_list_item_template, parent, false);
        SchoolViewHolder vh = new SchoolViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        School school = mSchools.get(position);

        ((SchoolViewHolder) holder).titleView.setText(school.getName());
        ((SchoolViewHolder) holder).school = school;
    }

    @Override
    public int getItemCount() {
        return mSchools.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, School school);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
