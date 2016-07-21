package com.pascoapp.wba02_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.firebasePojos.Course;
import com.pascoapp.wba02_android.firebasePojos.Test;


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

        View view = inflater.inflate(R.layout.fragment_test_overview, container, false);

        getCourseCode(setCourseCode);

//        setTestName(getTestName());
//        setTestDuration(getTestDuration());
//
//        setTestLecturer(getTestLecturer());
//        setTestProgrammes(getTestProgrammes());
//        setTestInstructions(getTestInstructions());

        setTestStartButton();
        // Inflate the layout for this fragment
        return view;
    }

    private void getCourseCode(final ValueDisplayer valueDisplayer) {
        DatabaseReference courseRef = FirebaseDatabase.getInstance().getReference()
                .child("courses").child(mTest.getCourse());

        courseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Course course = dataSnapshot.getValue(Course.class);
                valueDisplayer.displayString(course.getCode());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO: Return null or something
            }
        });
    }

    ValueDisplayer setCourseCode = new ValueDisplayer() {
        @Override
        public void displayString(String value) {
            TextView textView = (TextView) getActivity().findViewById(R.id.courseCode);
            textView.setText(value);
        }
    };

    private void setTestStartButton() {
        Button startButton = (Button) getActivity().findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        startButton.setVisibility(View.VISIBLE);
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
