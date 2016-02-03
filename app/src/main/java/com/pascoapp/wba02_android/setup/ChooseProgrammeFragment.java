package com.pascoapp.wba02_android.setup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.parseSubClasses.School;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseProgrammeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ArrayList<Programme> mProgrammes = new ArrayList<Programme>();
    private ProgressBar loadingIndicator;
    private LinearLayoutManager mLayoutManager;
    private ChooseProgrammeAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public ChooseProgrammeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_choose_programme, container, false);

        loadingIndicator = (ProgressBar) view.findViewById(R.id.loading_indicator);

        // set the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.programmes_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create a list for the adapter
        mProgrammes = new ArrayList<>();
        mAdapter = new ChooseProgrammeAdapter(mProgrammes);
        mAdapter.setOnItemClickListener(new ChooseProgrammeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Programme programme) {
                selectProgramme(programme);
            }
        });

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        School school = null;
        if (mListener != null) school = mListener.getSchool();
        fillProgrammeList(school);

        return view;
    }

    public void fillProgrammeList(School school) {
        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Programme> query = Programme.getQuery();
        query.selectKeys(Arrays.asList("name"));

        //filter for selected school if user has chosen his school already
        if (school != null) {
            query.whereEqualTo("school", school);
        }

        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ONLY);
        query.findInBackground(new FindCallback<Programme>() {
            @Override
            public void done(List<Programme> programmes, ParseException e) {
                if (e == null) {
                    mProgrammes.clear();
                    mProgrammes.addAll(programmes);
                    mAdapter.notifyDataSetChanged();
                    loadingIndicator.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(),
                            e.getCode() + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    private void selectProgramme(Programme programme) {
        if (mListener != null) {
            mListener.onProgrammeSelected(programme);
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
        void onProgrammeSelected(Programme programme);
        School getSchool();
    }

}
