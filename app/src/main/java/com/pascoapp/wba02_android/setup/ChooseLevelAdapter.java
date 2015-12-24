package com.pascoapp.wba02_android.setup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Level;

import java.util.List;

/**
 * Created by Kwaku on 12/23/2015.
 */
public class ChooseLevelAdapter extends RecyclerView.Adapter {

    private List<Level> mLevels;
    private OnItemClickListener mItemClickListener;

    public ChooseLevelAdapter(List<Level> mLevel) {
        this.mLevels = mLevel;
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public Level level;
        public LevelViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.level_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, level);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.level_list_item_template, parent, false);
        LevelViewHolder vh = new LevelViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Level level = mLevels.get(position);
        ((LevelViewHolder) holder).titleView.setText(level.getName());
        ((LevelViewHolder) holder).level = level;
    }

    @Override
    public int getItemCount() {
        return mLevels.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Level level);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
