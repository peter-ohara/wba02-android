package com.pascoapp.wba02_android.setup;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.School;
import com.pascoapp.wba02_android.parseSubClasses.Student;

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
public class ChooseSchoolFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<School> mSchools;
    private ProgressBar loadingIndicator;
    private LinearLayoutManager mLayoutManager;
    private ChooseSchoolAdapter mAdapter;
    private RecyclerView mRecyclerView;

    // TODO: Rename and change types of parameters
    public static ChooseSchoolFragment newInstance(String param1, String param2) {
        ChooseSchoolFragment fragment = new ChooseSchoolFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ChooseSchoolFragment() {
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
        View view = inflater.inflate(R.layout.content_choose_school, container, false);

        loadingIndicator = (ProgressBar) view.findViewById(R.id.loading_indicator);

        // set the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.schools_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create an object for the adapter
        mSchools = new ArrayList<>();
        mAdapter = new ChooseSchoolAdapter(mSchools);
        mAdapter.setOnItemClickListener(new ChooseSchoolAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, School school) {
                selectSchool(school);
            }
        });

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        fillSchoolList();

        return view;
    }

    public void fillSchoolList() {
        loadingIndicator.setVisibility(View.GONE);

        ParseQuery<School> schoolQuery = School.getQuery();
        schoolQuery.findInBackground(new FindCallback<School>() {
            @Override
            public void done(List<School> schools, ParseException e) {
                if (e == null) {
                    mSchools.clear();
                    mSchools.addAll(schools);

                    //populate list view with schools with an adapter notify
                    mAdapter.notifyDataSetChanged();
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    private void selectSchool(School school) {
        if (mListener != null) {
            mListener.onSchoolSelected(school);
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
        void onSchoolSelected(School school);
    }

}
