package com.pascoapp.wba02_android.setup;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.takeTest.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kwaku on 12/23/2015.
 */
public class ChooseSemesterAdapter extends RecyclerView.Adapter {

    public static final String SEMESTER_ERROR = "An Error Occured: Please report this in the alpha release group";
    private List<Integer> mSemesters;
    private OnItemClickListener mItemClickListener;

    public ChooseSemesterAdapter(ArrayList<Integer> mSemester) {
        this.mSemesters = mSemester;
    }

    public class SemesterViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public Integer semester;

        public SemesterViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.semester_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, semester);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.semester_list_item_template, parent, false);
        SemesterViewHolder vh = new SemesterViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Integer semester = mSemesters.get(position);
        String semesterStr;

        // TODO: Change year strings to variables that can be switched
        // based on the schools naming conventions

        switch(semester) {
            case 1:
                semesterStr = "First Semester";
                break;
            case 2:
                semesterStr = "Second Semester";
                break;
            default:
                // TODO: Take this out in beta release and implement proper error handling
                semesterStr = SEMESTER_ERROR;
        }

        ((SemesterViewHolder) holder).titleView.setText(semesterStr);

        ((SemesterViewHolder) holder).semester = semester;
    }

    @Override
    public int getItemCount() {
        return mSemesters.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Integer semester);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
