package com.pascoapp.wba02_android.setup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kwaku on 12/23/2015.
 */
public class ChooseLevelAdapter extends RecyclerView.Adapter {

    public static final String LEVEL_ERROR = "An Error Occured: Please report this in the alpha release group";
    private List<Integer> mLevels;
    private OnItemClickListener mItemClickListener;

    public ChooseLevelAdapter(ArrayList<Integer> mLevel) {
        this.mLevels = mLevel;
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public Integer level;

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
        Integer level = mLevels.get(position);
        String levelStr;

        // TODO: Change year strings to variables that can be switched
        // based on the schools naming conventions

        switch(level) {
            case 1:
                levelStr = "First Year";
                break;
            case 2:
                levelStr = "Second Year";
                break;
            case 3:
                levelStr = "Third Year";
                break;
            case 4:
                levelStr = "Fourth Year";
                break;
            case 5:
                levelStr = "Fifth Year";
                break;
            case 6:
                levelStr = "6th Year";
                break;
            case 7:
                levelStr = "7th Year";
                break;
            default:
                // TODO: Take this out in beta release and implement proper error handling
                levelStr = LEVEL_ERROR;
        }

        ((LevelViewHolder) holder).titleView.setText(levelStr);

        ((LevelViewHolder) holder).level = level;
    }

    @Override
    public int getItemCount() {
        return mLevels.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Integer level);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
