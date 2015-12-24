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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.parseSubClasses.School;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ChooseProgrammeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<Programme> mProgrammes = new ArrayList<Programme>();
    private ProgressBar loadingIndicator;
    private LinearLayoutManager mLayoutManager;
    private ChooseProgrammeAdapter mAdapter;
    private RecyclerView mRecyclerView;

    // TODO: Rename and change types of parameters
    public static ChooseProgrammeFragment newInstance(String param1, String param2) {
        ChooseProgrammeFragment fragment = new ChooseProgrammeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ChooseProgrammeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        fillProgrammeList(school);

        return view;
    }

    public void fillProgrammeList(School school) {
        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Programme> query = Programme.getQuery();

        //filter for selected school if user has chosen his school already
        if (school != null) {
            query.whereEqualTo("school", school);
        }

        query.findInBackground(new FindCallback<Programme>() {
            @Override
            public void done(List<Programme> programmes, ParseException e) {
                loadingIndicator.setVisibility(View.GONE);
                if (e == null) {
                    mProgrammes.addAll(programmes);

                    //populate list view with programmes with an adapter notify
                    mAdapter.notifyDataSetChanged();
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
    }

}
