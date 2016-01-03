package com.pascoapp.wba02_android.setup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Student;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ChooseLevelFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ArrayList<Integer> mLevels;
    private LinearLayoutManager mLayoutManager;
    private ChooseLevelAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public ChooseLevelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_choose_level, container, false);

        // set the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.levels_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create a list for the adapter
        mLevels = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            mLevels.add(i);
        }

        mAdapter = new ChooseLevelAdapter(mLevels);
        mAdapter.setOnItemClickListener(new ChooseLevelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Integer level) {
                selectLevel(level);
            }
        });

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        return view;
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

    private void selectLevel(Integer level){
        if (mListener != null) {
            mListener.onLevelSelected(level);
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
        void onLevelSelected(Integer level);
    }

}
