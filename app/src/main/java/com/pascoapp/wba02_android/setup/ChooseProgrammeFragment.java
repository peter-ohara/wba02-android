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
import com.pascoapp.wba02_android.parseSubClasses.Programme;

public class ChooseProgrammeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private LinearLayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Programme, ProgrammeHolder> mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference mRef;

    public ChooseProgrammeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_choose_programme, container, false);

        mRef = FirebaseDatabase.getInstance().getReference().child("programmes");

        // set the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.programmes_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<Programme, ProgrammeHolder>(Programme.class, R.layout.programme_list_item_template, ProgrammeHolder.class, mRef) {
            @Override
            public void populateViewHolder(ProgrammeHolder programmeViewHolder, Programme programme, int position) {
                String programmeKey = getRef(position).getKey();
                programmeViewHolder.setTitle(programme.getName());
                programmeViewHolder.setOnClickListeners(programmeKey, mListener);
            }
        };


        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        String schoolKey = null;
        if (mListener != null) schoolKey = mListener.getSchoolKey();
        fillProgrammeList(schoolKey);

        return view;
    }

    public void fillProgrammeList(String schoolKey) {
        // Todo: Filter results by school
    }

    private void selectProgramme(String programmeKey) {
        if (mListener != null) {
            mListener.onProgrammeSelected(programmeKey);
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

    public static class ProgrammeHolder extends RecyclerView.ViewHolder{
        View mView;

        public ProgrammeHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String name) {
            TextView field = (TextView) mView.findViewById(R.id.programme_name);
            field.setText(name);
        }

        public void setOnClickListeners(final String programmeKey,
                                        final OnFragmentInteractionListener mListener) {

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onProgrammeSelected(programmeKey);
                    }
                }
            });
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onProgrammeSelected(String programmeKey);
        String getSchoolKey();
    }

}
