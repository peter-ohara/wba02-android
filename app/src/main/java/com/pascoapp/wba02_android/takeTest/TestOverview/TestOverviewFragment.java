package com.pascoapp.wba02_android.takeTest.TestOverview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.firebasePojos.Programme;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.databinding.FragmentTestOverviewBinding;
import com.pascoapp.wba02_android.firebasePojos.Test;
import com.pascoapp.wba02_android.takeTest.TestViewModel;

import java.util.ArrayList;


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
    private static final String ARG_TEST = "test";

    private Test mTest;

    private OnFragmentInteractionListener mListener;

    public TestOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TestOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestOverviewFragment newInstance(Test test) {
        TestOverviewFragment fragment = new TestOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TEST, test);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTest = (Test) getArguments().getSerializable(ARG_TEST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTestOverviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test_overview, container, false);
        View view = binding.getRoot();
        binding.setTest(new TestViewModel());

        setProgrammes(view);
        setInstructions(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void setProgrammes(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.programmesList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        TestViewModel test = new TestViewModel();
        ArrayList<Programme> programmes = (ArrayList<Programme>) test.getProgrammes();

        // specify an adapter (see also next example)
        ProgrammesAdapter adapter = new ProgrammesAdapter(programmes);
        recyclerView.setAdapter(adapter);
    }

    private void setInstructions(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.instructionsList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        TestViewModel test = new TestViewModel();
        ArrayList<String> instructions = (ArrayList<String>) test.getInstructions();

        // specify an adapter (see also next example)
        InstructionsAdapter adapter = new InstructionsAdapter(instructions);
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
