package com.pascoapp.wba02_android.setup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Programme;

import java.util.List;

/**
 * Created by Kwaku on 12/23/2015.
 */
public class ChooseProgrammeAdapter  extends RecyclerView.Adapter {

    private List<Programme> mProgrammes;
    private OnItemClickListener mItemClickListener;

    public ChooseProgrammeAdapter(List<Programme> mProgrammes) {
        this.mProgrammes = mProgrammes;
    }

    public class ProgrammeViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public Programme programme;

        public ProgrammeViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.programme_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, programme);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.programme_list_item_template, parent, false);
        ProgrammeViewHolder vh = new ProgrammeViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Programme programme = mProgrammes.get(position);
        ((ProgrammeViewHolder) holder).titleView.setText(programme.getName());
        ((ProgrammeViewHolder) holder).programme = programme;
    }

    @Override
    public int getItemCount() {
        return mProgrammes.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Programme programme);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
