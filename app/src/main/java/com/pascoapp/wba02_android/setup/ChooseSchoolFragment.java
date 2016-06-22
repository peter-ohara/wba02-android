package com.pascoapp.wba02_android.setup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.School;

public class ChooseSchoolFragment extends Fragment{

    private OnFragmentInteractionListener mListener;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<School, SchoolHolder> mAdapter;
    private DatabaseReference mRef;

    public ChooseSchoolFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_choose_school, container, false);

        mRef = FirebaseDatabase.getInstance().getReference().child("schools");

        // set the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.schools_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<School, SchoolHolder>(School.class, R.layout.school_list_item_template, SchoolHolder.class, mRef) {
            @Override
            public void populateViewHolder(SchoolHolder schoolViewHolder, School school, int position) {
                String schoolKey = getRef(position).getKey();
                schoolViewHolder.setTitle(school.getName());
                schoolViewHolder.setOnClickListeners(schoolKey, mListener);
            }
        };

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void selectSchool(String schoolKey) {
        if (mListener != null) {
            mListener.onSchoolSelected(schoolKey);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    public static class SchoolHolder extends RecyclerView.ViewHolder{
        View mView;

        public SchoolHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String name) {
            TextView field = (TextView) mView.findViewById(R.id.school_name);
            field.setText(name);
        }

        public void setOnClickListeners(final String schoolKey,
                                        final OnFragmentInteractionListener mListener) {

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onSchoolSelected(schoolKey);
                    }
                }
            });
        }
    }


    public interface OnFragmentInteractionListener {
        void onSchoolSelected(String schoolKey);
    }

}
