package com.pascoapp.wba02_android.takeTest.TestOverview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.dataFetching.Programme;
import com.pascoapp.wba02_android.takeTest.TestViewModel;

import java.util.ArrayList;

import trikita.jedux.Action;
import trikita.jedux.Store;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestOverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestOverviewFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_TEST_KEY = "testKey";

    private String mTestKey;

    private OnFragmentInteractionListener mListener;

    private Store<Action, State> store;

    public TestOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TestOverviewFragment.
     * @param testKey
     */
    // TODO: Rename and change types and number of parameters
    public static TestOverviewFragment newInstance(String testKey) {
        TestOverviewFragment fragment = new TestOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TEST_KEY, testKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTestKey = getArguments().getString(ARG_TEST_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return null;
    }

    private void setProgrammes(View view, TestViewModel testViewModel) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.programmesList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Programme> programmes = (ArrayList<Programme>) testViewModel.getProgrammes();

        ProgrammesAdapter adapter = new ProgrammesAdapter(programmes, store);
        recyclerView.setAdapter(adapter);
    }

    private void setInstructions(View view, final TestViewModel testViewModel) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.instructionsList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        final InstructionsAdapter adapter = new InstructionsAdapter(store);
        recyclerView.setAdapter(adapter);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
